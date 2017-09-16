package com.dtp.renav.interfaces

import android.content.Intent
import com.dtp.renav.NavigationView
import com.dtp.renav.base.SimpleNavigationAdapter.Row

/**
 * Created by ner on 7/12/17.
 */
interface NavigationManager {

    fun attachNavigationView(navView: NavigationView)

    fun setAdapter(adapter: NavigationAdapter?)

    fun columnSelected(columnId: Int)

    fun onResume()
    fun onPause()
    fun onDestroy()

    fun pushRowToCurrentColumn(row: Row<*>)
    fun popCurrentRowColumn()

    fun startActivityForResult(intent: Intent, requestCode: Int)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)

    fun handleBack(): Boolean
}