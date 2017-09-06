package com.dtp.renav.interfaces

/**
 * Created by ner on 7/12/17.
 */
interface NavigationAdapter {

    fun getRowId(columnId: Int): Int
    fun createRowViewHolderForId(container: NavigatorContainer, rowId: Int): RowViewHolder<*>
    fun bindColumnView(columnId: Int, rowViewHolder: RowViewHolder<*>)
    fun handleBack(columnId: Int): Boolean
}