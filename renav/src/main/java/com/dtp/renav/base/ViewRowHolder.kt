package com.dtp.renav.base

import android.content.Intent
import android.view.ViewGroup
import com.dtp.renav.interfaces.NavigationManager
import com.dtp.renav.interfaces.RowHolder

/**
 * Created by ner on 9/6/17.
 */
abstract class ViewRowHolder<in T> : RowHolder<T> {

    private var navigationManager: NavigationManager? = null

    abstract val rootView: ViewGroup

    override fun onAttach(navigationManager: NavigationManager) {
        this.navigationManager = navigationManager
    }

    override fun onDetach() {
        navigationManager = null
    }

    override fun pushRowToCurrentColumn(row: SimpleNavigationAdapter.Row<*>) {
        navigationManager?.pushRowToCurrentColumn(row)
    }

    override fun popCurrentColumnRow() {
        navigationManager?.popCurrentRowColumn()
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        navigationManager?.startActivityForResult(intent, requestCode)
    }
}