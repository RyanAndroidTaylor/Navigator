package com.dtp.renav

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.dtp.renav.interfaces.NavigationManager
import kotlin.math.min

/**
 * Created by ner on 9/16/17.
 */
// I pulled this out of the NavigationView because I was running into problems where the shadow of this view was being drawn bellow the container view.
// Now this view is added to the NavigationView after it's container view so it will be rendered on top of the container.
class NavigationBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val selectedTextHeight = 14
    private val normalTextHeight = 12

    private val backgroundPaint = Paint()
    private val shadowPaint = Paint()

    private var selectedColor: Int
    private var unselectedColor: Int = ContextCompat.getColor(context, R.color.text_black)

    private val textPaint = TextPaint()

    private val columns = mutableListOf<Column>()

    private var initialTab: Int = -1

    private val tabRect = Rect()

    private var columnWidth = 0

    var navigationManager: NavigationManager? = null

    init {
        layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)

        (layoutParams as FrameLayout.LayoutParams).gravity = Gravity.BOTTOM

        backgroundPaint.color = ContextCompat.getColor(context, R.color.nav_view_background)
        backgroundPaint.style = Paint.Style.FILL

        val shadow = ContextCompat.getColor(context, R.color.nav_view_shadow)

        shadowPaint.shader = LinearGradient(0f, BOTTOM_BAR_SHADOW_HEIGHT.toFloat(), 0f, 0f, shadow, Color.TRANSPARENT, Shader.TileMode.CLAMP)

        val appAccentColor = TypedValue()
        context.theme.resolveAttribute(R.attr.colorAccent, appAccentColor, true)

        selectedColor = appAccentColor.data
    }

    fun setInitialValues(initialTab: Int, selectedColor: Int, unselectedColor: Int) {
        if (initialTab != -1)
            this.initialTab = initialTab
        if (selectedColor != -1)
            this.selectedColor = selectedColor
        if (unselectedColor != -1)
            this.unselectedColor = unselectedColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val measureWidth = MeasureSpec.getSize(widthMeasureSpec)

        val wrapContentWidth = columns.size * MIN_COLUMN_WIDTH

        val width = when (widthMode) {
            MeasureSpec.UNSPECIFIED -> measureWidth
            MeasureSpec.AT_MOST -> min(widthMode, wrapContentWidth)
            MeasureSpec.EXACTLY -> measureWidth
            else -> measureWidth
        }

        val height = BOTTOM_BAR_HEIGHT + BOTTOM_BAR_SHADOW_HEIGHT

        columnWidth = width / columns.size

        columns.forEachIndexed { index, tab ->
            tab.bounds.set(columnWidth * index, 0, columnWidth * (index + 1), BOTTOM_BAR_HEIGHT)
        }

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        val topOfBottomBar = height.toFloat() - BOTTOM_BAR_HEIGHT - BOTTOM_BAR_SHADOW_HEIGHT

        canvas.drawRect(0f, topOfBottomBar + BOTTOM_BAR_SHADOW_HEIGHT, width.toFloat(), height.toFloat(), backgroundPaint)
        canvas.drawRect(0f, topOfBottomBar, width.toFloat(), topOfBottomBar + BOTTOM_BAR_SHADOW_HEIGHT, shadowPaint)

        columns.forEachIndexed { index, tab ->
            val textSize: Float

            if (tab.isSelected) {
                tab.icon.setColorFilter(selectedColor, PorterDuff.Mode.SRC_IN)
                textPaint.color = selectedColor
                textSize = spToPx(selectedTextHeight)
            } else {
                tab.icon.setColorFilter(unselectedColor, PorterDuff.Mode.SRC_IN)
                textPaint.color = unselectedColor
                textSize = spToPx(normalTextHeight)
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

    override fun setBackgroundColor(color: Int) {
        backgroundPaint.color = color
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (initialTab != -1)
            columnSelected(initialTab)
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

    fun setSelectedColor(color: Int) {
        selectedColor = color
    }

    fun setUnselectedColor(color: Int) {
        unselectedColor = color
    }

    fun addColumn(itemId: Int, title: String, iconId: Int) {
        try {
            val drawable = VectorDrawableCompat.create(context.resources, iconId, null)

            columns.add(Column(itemId, title, drawable!!))
        } catch (exception: Resources.NotFoundException) {
            throw Resources.NotFoundException("Invalid resource ID for tab at index ${columns.size}")
        }
    }

    fun columnSelected(columnId: Int) {
        columns.forEach {
            it.isSelected = it.id == columnId
        }

        navigationManager?.columnSelected(columnId)

        invalidate()
    }

    class Column(val id: Int, val title: String, val icon: VectorDrawableCompat) {
        val bounds = Rect(0, 0, 0, 0)

        var isSelected = false
    }
}