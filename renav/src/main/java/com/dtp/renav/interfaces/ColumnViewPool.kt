package com.dtp.renav.interfaces

/**
 * Created by ner on 7/12/17.
 */
interface ColumnViewPool {

    fun getRowViewHolder(rowId: Int): RowViewHolder<*>?
    fun putRowViewHolder(rowId: Int, rowViewHolder: RowViewHolder<*>)
}