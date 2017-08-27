package com.dtp.renav

/**
 * Created by ner on 7/12/17.
 */
interface NavigationManager {

    fun attachNavView(navView: NavigationView)

    fun setAdapter(adapter: NavigationAdapter?)

    fun columnSelected(columnId: Int)

    fun handleBack(): Boolean
}