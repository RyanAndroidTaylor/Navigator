package com.dtp.renav

import android.view.ViewGroup

/**
 * Created by ner on 7/12/17.
 */
interface NavigationAdapter {

    fun getRowId(columnId: Int): Int
    fun createRowViewHolderForId(parent: ViewGroup, rowId: Int): RowViewHolder<*>
    fun bindColumnView(columnId: Int, rowViewHolder: RowViewHolder<*>)
}