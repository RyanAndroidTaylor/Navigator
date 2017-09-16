package com.dtp.renav.interfaces

import android.content.Intent
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

    fun startActivityForResult(intent: Intent, requestCode: Int)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {}
}