package com.dtp.renav.base

import android.content.Context
import android.content.Intent
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.ViewGroup
import com.dtp.renav.interfaces.NavigationManager
import com.dtp.renav.interfaces.RowHolder

/**
 * Created by ner on 9/6/17.
 */
abstract class ViewRowHolder<in T> : RowHolder<T> {

    private var navigationManager: NavigationManager? = null

    abstract val rootView: ViewGroup

    protected val context: Context
        get() = rootView.context

    override fun onAttach(navigationManager: NavigationManager) {
        this.navigationManager = navigationManager
    }

    override fun onDetach() {
        navigationManager = null
    }

    override fun pushRow(row: SimpleNavigationAdapter.Row<*>) {
        navigationManager?.pushRow(row)
    }

    override fun popRow() {
        navigationManager?.popRow()
    }

    override fun setSupportActionBar(toolbar: Toolbar) {
        navigationManager?.setSupportActionBar(toolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return false
    }

    override fun setDisplayHomeAsUpEnabled(enabled: Boolean) {
        navigationManager?.setDisplayHomeAsUpEnabled(enabled)
    }

    override fun hasPermission(permission: String): Boolean =
            navigationManager?.checkPermission(permission) ?: false

    override fun requestPermission(requestCode: Int, permissions: Array<String>) {
        navigationManager?.requestPermission(requestCode, permissions)
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        navigationManager?.startActivityForResult(intent, requestCode)
    }
}