package com.dtp.renav.interfaces

import android.content.Intent
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.dtp.renav.base.SimpleNavigationAdapter.Row

/**
 * Created by ner on 7/12/17.
 */
interface RowHolder<in T> {
    fun bind(item: T)

    fun onAttach(navigationManager: NavigationManager) {}
    fun onDetach() {}

    fun onResume() {}
    fun onPause() {}
    fun onDestroy() {}

    fun pushRowToCurrentColumn(row: Row<*>)
    fun popCurrentColumnRow()

    fun onOptionsItemSelected(item: MenuItem?): Boolean

    fun setSupportActionBar(toolbar: Toolbar)
    fun setDisplayHomeAsUpEnabled(enabled: Boolean)

    fun hasPermission(permission: String): Boolean
    fun requestPermission(requestCode: Int, permissions: Array<String>)
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {}

    fun startActivityForResult(intent: Intent, requestCode: Int)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}
}