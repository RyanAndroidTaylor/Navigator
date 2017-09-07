package com.dtp.renav

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.util.Xml
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.dtp.renav.interfaces.NavigationManager
import com.dtp.renav.interfaces.NavigatorContainer
import com.dtp.renav.interfaces.RowHolder
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

class NavigationView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    val ICON_SIZE = dpToPx(24)
    val BOTTOM_SPACING = dpToPx(10)
    val BOTTOM_BAR_HEIGHT = dpToPx(56)
    val MIN_COLUMN_WIDTH = dpToPx(80)

    private val textPaint: Paint
    private var selectedColor: Int
    private var unselectedColor: Int = ContextCompat.getColor(context, R.color.text_black)

    private val columns = mutableListOf<Column>()

    private val tabRect = Rect()

    private var columnWidth = 0

    private lateinit var rootContainerView: ViewGroup
    lateinit var container: NavigatorContainer
        private set

    var navigationManager: NavigationManager? = null
        set(value) {
            field = value

            field?.attachNavigationView(this)

            selectColumn(initialTab)
        }

    private var initialTab: Int = -1

    init {
        setWillNotDraw(false)

        val appAccentColor = TypedValue()
        context.theme.resolveAttribute(R.attr.colorAccent, appAccentColor, true)

        selectedColor = appAccentColor.data

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationView)

            inflateMenu(typedArray.getResourceId(R.styleable.NavigationView_navigation_menu, -1))

            initialTab = typedArray.getResourceId(R.styleable.NavigationView_default_column, if (columns.isNotEmpty()) columns[0].id else -1)

            selectedColor = typedArray.getColor(R.styleable.NavigationView_selected_color, selectedColor)
            unselectedColor = typedArray.getColor(R.styleable.NavigationView_unselected_color, unselectedColor)

            typedArray.recycle()
        }

        textPaint = Paint(unselectedColor)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val measureWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val measureHeight = View.MeasureSpec.getSize(heightMeasureSpec)

        val wrapContentWidth = columns.size * MIN_COLUMN_WIDTH

        val width = when (widthMode) {
            View.MeasureSpec.UNSPECIFIED -> {
                measureWidth
            }
            View.MeasureSpec.AT_MOST -> {
                Math.min(widthMode, wrapContentWidth)
            }
            View.MeasureSpec.EXACTLY -> measureWidth
            else -> measureWidth
        }

        val height = when (heightMode) {
            View.MeasureSpec.UNSPECIFIED -> {
                BOTTOM_BAR_HEIGHT
            }
            View.MeasureSpec.AT_MOST -> {
                Math.min(measureHeight, BOTTOM_BAR_HEIGHT)
            }
            View.MeasureSpec.EXACTLY -> measureHeight
            else -> measureHeight
        }

        columnWidth = width / columns.size

        columns.forEachIndexed { index, tab ->
            tab.bounds.set(columnWidth * index, measureHeight - BOTTOM_BAR_HEIGHT, columnWidth * (index + 1), height)
        }

        measureContainer(measureWidth, measureHeight)

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        columns.forEachIndexed { index, tab ->
            val textSize: Float

            if (tab.isSelected) {
                tab.icon.setColorFilter(selectedColor, PorterDuff.Mode.SRC_IN)
                textPaint.color = selectedColor
                textSize = spToPx(14)
            } else {
                tab.icon.setColorFilter(unselectedColor, PorterDuff.Mode.SRC_IN)
                textPaint.color = unselectedColor
                textSize = spToPx(12)
            }

            textPaint.textSize = textSize

            val textWidth = textPaint.measureText(tab.title)

            canvas.drawText(tab.title, (index * columnWidth) + (columnWidth / 2) - (textWidth / 2), height - BOTTOM_SPACING.toFloat(), textPaint)

            val left = (index * columnWidth) + (columnWidth / 2) - (ICON_SIZE / 2)
            val top = height - BOTTOM_SPACING - ICON_SIZE - textSize.toInt()

            tabRect.set(left, top, left + ICON_SIZE, top + ICON_SIZE)
            tab.icon.bounds = tabRect

            tab.icon.draw(canvas)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        insureContainerIsCorrect()

        rootContainerView = getChildAt(0) as ViewGroup

        container.setRootContainerView(rootContainerView)

        if (initialTab != -1)
            columnSelected(initialTab)
    }

    private fun insureContainerIsCorrect() {
        if (childCount > 1 || childCount < 1)
            throw IllegalStateException("NavigationView must have only one child")
        if (getChildAt(0) !is ViewGroup)
            throw IllegalStateException("NavigationView child must be a ViewGroup")
    }

    fun attachContainer(container: NavigatorContainer) {
        this.container = container
    }

    fun handleBack(): Boolean = navigationManager?.handleBack() ?: false

    private fun measureContainer(measureWidth: Int, measureHeight: Int) {
        val childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY)
        val childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(measureHeight - BOTTOM_BAR_HEIGHT, MeasureSpec.EXACTLY)

        getChildAt(0).measure(childWidthMeasureSpec, childHeightMeasureSpec)
    }

    private fun inflateMenu(menuId: Int) {
        if (menuId == -1)
            return

        try {
            val parser = context.resources.getLayout(menuId)
            val attributeSet = Xml.asAttributeSet(parser)

            val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.NavigationView)

            var endOfXml = false
            var eventType = parser.eventType

            while (!endOfXml) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        when (parser.name) {
                            "item" -> readItem(attributeSet)
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        when (parser.name) {
                            "menu" -> endOfXml = true
                        }
                    }
                }

                eventType = parser.next()
            }

            typedArray.recycle()
        } catch (xmlException: XmlPullParserException) {
            xmlException.printStackTrace()
        }
    }

    private fun readItem(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, android.support.v7.appcompat.R.styleable.MenuItem)

        val itemId = typedArray.getResourceId(android.support.v7.appcompat.R.styleable.MenuItem_android_id, -1)
        val itemTitle = typedArray.getText(android.support.v7.appcompat.R.styleable.MenuItem_android_title)
        val itemIconResId = typedArray.getResourceId(android.support.v7.appcompat.R.styleable.MenuItem_android_icon, -1)

        addColumn(itemId, itemTitle.toString(), itemIconResId)

        typedArray.recycle()
    }

    private fun addColumn(itemId: Int, title: String, iconId: Int) {
        try {
            val drawable = ContextCompat.getDrawable(context, iconId).mutate()

            columns.add(Column(itemId, title, drawable))
        } catch (exception: Resources.NotFoundException) {
            throw Resources.NotFoundException("Invalid resource ID for tab at index ${columns.size}")
        }
    }

    private fun columnSelected(columnId: Int) {
        columns.forEach {
            it.isSelected = it.id == columnId
        }

        navigationManager?.columnSelected(columnId)

        invalidate()
    }

    fun selectColumn(columnId: Int) {
        if (isAttachedToWindow)
            columnSelected(columnId)
    }

    fun detachCurrentRowViewHolder() {
        container.detachCurrentViewHolder()
    }

    fun attachRowViewHolder(holder: RowHolder<*>) {
        container.attachViewHolder(holder)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            columns.forEach {
                if (it.bounds.contains(event.x.toInt(), event.y.toInt())) {
                    columnSelected(it.id)

                    return true
                }
            }
        }

        return false
    }

    class Column(val id: Int, val title: String, val icon: Drawable) {
        val bounds = Rect(0, 0, 0, 0)

        var isSelected = false
    }

    private fun dpToPx(dp: Int) = Math.round(dp * (Resources.getSystem().displayMetrics.densityDpi / 160f))
    private fun spToPx(sp: Int) = sp * Resources.getSystem().displayMetrics.scaledDensity
}