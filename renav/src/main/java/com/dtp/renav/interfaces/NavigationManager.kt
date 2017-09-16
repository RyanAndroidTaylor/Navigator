package com.dtp.renav.interfaces

import com.dtp.renav.NavigationView

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

    fun handleBack(): Boolean
}