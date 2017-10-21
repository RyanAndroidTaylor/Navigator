package com.dtp.renav.interfaces

import android.view.LayoutInflater
import com.dtp.renav.base.SimpleNavigationAdapter.Row

/**
 * Created by ner on 7/12/17.
 */
interface NavigationAdapter {

    fun getRowId(columnId: Int): Int
    fun createRowViewHolderForId(layoutInflater: LayoutInflater, container: NavigationContainer, rowId: Int): RowHolder<*>
    fun bindColumnView(columnId: Int, rowHolder: RowHolder<*>)

    fun pushRow(columnId: Int, row: Row<*>)
    fun popRow(columnId: Int)

    fun handleBack(columnId: Int): Boolean
}