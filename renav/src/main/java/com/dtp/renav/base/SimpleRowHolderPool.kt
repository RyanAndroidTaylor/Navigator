package com.dtp.renav.base

import com.dtp.renav.interfaces.RowHolder
import com.dtp.renav.interfaces.RowHolderPool

/**
 * Created by ner on 7/15/17.
 */
class SimpleRowHolderPool : RowHolderPool {

    private val rowViewHolders = mutableMapOf<Int, RowHolder<*>>()

    override fun getRowViewHolder(rowId: Int): RowHolder<*>? {
        if (rowViewHolders.containsKey(rowId))
            return rowViewHolders[rowId]

        return null
    }

    override fun putRowViewHolder(rowId: Int, rowHolder: RowHolder<*>) {
        if (!rowViewHolders.containsKey(rowId))
            rowViewHolders[rowId] = rowHolder
    }

    override fun destroyRowHolders() {
        rowViewHolders.values.forEach { it.onDestroy() }

        rowViewHolders.clear()
    }
}