package com.dtp.renav.interfaces

/**
 * Created by ner on 7/12/17.
 */
interface RowHolderPool {

    fun getRowViewHolder(rowId: Int): RowHolder<*>?
    fun putRowViewHolder(rowId: Int, rowHolder: RowHolder<*>)

    fun destroyRowHolders()
}