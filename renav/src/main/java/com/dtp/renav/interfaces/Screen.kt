package com.dtp.renav.interfaces

import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.dtp.renav.base.SimpleNavigationAdapter.ScreenData

/**
 * Created by ner on 7/12/17.
 */
interface Screen<in T> {
    var activity: Activity?

    fun bind(item: T)

    fun onAttach(activity: Activity?, navigationManager: NavigationManager) {}
    fun onDetach() {}

    fun onResume() {}
    fun onPause() {}
    fun onDestroy() {}

    fun pushScreen(screenData: ScreenData<*>)
    fun popScreen()

    fun onOptionsItemSelected(item: MenuItem?): Boolean

    fun setSupportActionBar(toolbar: Toolbar)
    fun setDisplayHomeAsUpEnabled(enabled: Boolean)

    fun hasPermission(permission: String): Boolean
    fun requestPermission(requestCode: Int, permissions: Array<String>)
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {}

    fun startActivityForResult(intent: Intent, requestCode: Int)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}
}