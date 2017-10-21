package com.dtp.renav

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.util.Xml
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.dtp.renav.base.ViewNavigationContainer
import com.dtp.renav.interfaces.NavigationContainer
import com.dtp.renav.interfaces.NavigationManager
import com.dtp.renav.interfaces.RowHolder
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

class NavigationView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    private var activity: AppCompatActivity? = null

    var container: NavigationContainer = ViewNavigationContainer()
        private set

    var navigationManager: NavigationManager? = null
        set(value) {
            field = value

            field?.attachNavigationView(this)

            navigationBar.navigationManager = value
        }

    private val navigationBar = NavigationBar(context)

    private lateinit var rootContainerView: ViewGroup

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationView)

            inflateMenu(typedArray.getResourceId(R.styleable.NavigationView_navigation_menu, -1))

            val initialTab = typedArray.getResourceId(R.styleable.NavigationView_default_column, -1)

            val selectedColor = typedArray.getColor(R.styleable.NavigationView_selected_color, -1)
            val unselectedColor = typedArray.getColor(R.styleable.NavigationView_unselected_color, -1)

            typedArray.recycle()

            navigationBar.setInitialValues(initialTab, selectedColor, unselectedColor)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measureWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val measureHeight = View.MeasureSpec.getSize(heightMeasureSpec)

        val childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY)
        val childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(measureHeight - BOTTOM_BAR_HEIGHT, MeasureSpec.EXACTLY)

        measureChildren(childWidthMeasureSpec, childHeightMeasureSpec)

        setMeasuredDimension(measureWidth, measureHeight)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (context is AppCompatActivity)
            activity = context as AppCompatActivity
        else
        //TODO We should not require them to use and AppCompatActivity but if they do we should warn them that some features will not be available.
        // When we make the switch to supporting normal Activities this will need to be tested well.
            throw IllegalStateException("Attaching to an Activity that does not extend AppCompatActivity. Activities that use this view must extend AppCompatActivity")

        addView(navigationBar)

        insureRootViewIsCorrect()

        rootContainerView = getChildAt(0) as ViewGroup

        container.setRootContainerView(rootContainerView)
    }

    override fun onDetachedFromWindow() {
        navigationManager?.onDestroy()

        super.onDetachedFromWindow()

        activity = null
    }

    private fun insureRootViewIsCorrect() {
        if (childCount > 2 || childCount < 2)
        // The second view is the NavigationBar and we add it when this view is created so we can guarantee
        // the second view is a NavigationBar if there are only 2 child views.
            throw IllegalStateException("NavigationView must have only one child")
        if (getChildAt(0) !is ViewGroup)
            throw IllegalStateException("NavigationView child must be a ViewGroup")
    }

    fun onOptionsItemSelected(item: MenuItem?): Boolean = navigationManager?.onOptionsItemSelected(item) == true

    fun setSupportActionBar(toolbar: Toolbar) {
        activity?.setSupportActionBar(toolbar)
    }

    fun setDisplayHomeAsUpEnabled(enabled: Boolean) {
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(enabled)
    }

    fun checkPermission(permission: String): Boolean =
            ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    fun requestPermission(requestCode: Int, permissions: Array<out String>) {
        activity?.let { ActivityCompat.requestPermissions(it, permissions, requestCode) }
    }

    fun startActivityForResult(intent: Intent, requestCode: Int) {
        activity?.startActivityForResult(intent, requestCode)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        navigationManager?.onActivityResult(requestCode, resultCode, data)
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        navigationManager?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun onPause() {
        navigationManager?.onPause()
    }

    fun onResume() {
        navigationManager?.onResume()
    }

    fun onDestroy() {
        navigationManager?.onDestroy()
    }

    fun attachContainer(container: NavigationContainer) {
        this.container = container
    }

    fun handleBack(): Boolean = navigationManager?.handleBack() ?: false

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

        navigationBar.addColumn(itemId, itemTitle.toString(), itemIconResId)

        typedArray.recycle()
    }

    fun selectColumn(columnId: Int) {
        if (isAttachedToWindow)
            navigationBar.columnSelected(columnId)
    }

    fun detachCurrentRowViewHolder() {
        container.detachCurrentViewHolder()
    }

    fun attachRowViewHolder(holder: RowHolder<*>) {
        container.attachViewHolder(holder)
    }
}