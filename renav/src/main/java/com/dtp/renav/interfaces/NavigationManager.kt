package com.dtp.renav.interfaces

import android.content.Intent
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.dtp.renav.NavigationView
import com.dtp.renav.base.SimpleNavigationAdapter.Row

/**
 * Created by ner on 7/12/17.
 */
interface NavigationManager {

    var shouldRecycleViews: Boolean

    fun attachNavigationView(navView: NavigationView)

    fun setAdapter(adapter: NavigationAdapter?)

    fun columnSelected(columnId: Int)

    fun onResume()
    fun onPause()
    fun onDestroy()

    fun pushRow(row: Row<*>)
    fun popRow()

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