package com.dtp.renav.interfaces

/**
 * Created by ner on 7/12/17.
 */
interface NavigationAdapter {

    fun getRowId(columnId: Int): Int
    fun createRowViewHolderForId(container: NavigatorContainer, rowId: Int): RowHolder<*>
    fun bindColumnView(columnId: Int, rowHolder: RowHolder<*>)
    fun handleBack(columnId: Int): Boolean
}