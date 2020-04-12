package com.dtp.renav.interfaces

/**
 * Created by ner on 7/12/17.
 */
interface RowHolderPool {

    fun getRowViewHolder(rowId: Int): Screen<*>?
    fun putRowViewHolder(rowId: Int, screen: Screen<*>)

    fun destroyRowHolders()
}