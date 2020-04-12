package com.dtp.renav.interfaces

import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.dtp.renav.NavigationView
import com.dtp.renav.base.SimpleNavigationAdapter.ScreenData

/**
 * Created by ner on 7/12/17.
 */
interface NavigationManager {

    var activity: Activity?

    var shouldRecycleViews: Boolean

    fun attachNavigationView(navView: NavigationView)

    fun setAdapter(adapter: NavigationAdapter?)

    fun columnSelected(columnId: Int)

    // TODO Make NavigationManager lifecycle aware
    fun onResume()
    fun onPause()
    fun onDestroy()

    fun pushScreen(screenData: ScreenData<*>)
    fun popScreen()

    fun onOptionsItemSelected(item: MenuItem?): Boolean

    fun setSupportActionBar(toolbar: Toolbar)
    fun setDisplayHomeAsUpEnabled(enabled: Boolean)

    fun checkPermission(permission: String): Boolean
    fun requestPermission(requestCode: Int, permissions: Array<String>)
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)

    fun startActivityForResult(intent: Intent, requestCode: Int)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    fun handleBack(): Boolean
}