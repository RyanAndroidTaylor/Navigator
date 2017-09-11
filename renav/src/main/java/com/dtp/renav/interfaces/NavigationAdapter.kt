package com.dtp.renav.interfaces

import android.view.LayoutInflater

/**
 * Created by ner on 7/12/17.
 */
interface NavigationAdapter {

    fun getRowId(columnId: Int): Int
    fun createRowViewHolderForId(layoutInflater: LayoutInflater, container: NavigationContainer, rowId: Int): RowHolder<*>
    fun bindColumnView(columnId: Int, rowHolder: RowHolder<*>)
    fun handleBack(columnId: Int): Boolean
}