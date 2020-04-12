package com.dtp.renav.interfaces

import android.view.LayoutInflater
import com.dtp.renav.base.SimpleNavigationAdapter.ScreenData

/**
 * Created by ner on 7/12/17.
 */
interface NavigationAdapter {

    fun getScreenId(columnId: Int): Int
    fun createRowViewHolderForId(layoutInflater: LayoutInflater, container: NavigationContainer, rowId: Int): Screen<*>
    fun bindColumnView(columnId: Int, screen: Screen<*>)

    fun pushScreen(columnId: Int, screenData: ScreenData<*>)
    fun popScreen(columnId: Int)

    fun handleBack(columnId: Int): Boolean
}