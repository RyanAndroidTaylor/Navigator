package com.dtp.renav.base

import com.dtp.renav.interfaces.RowViewHolder
import com.dtp.renav.interfaces.ColumnViewPool

/**
 * Created by ner on 7/15/17.
 */
class BaseColumnViewPool : ColumnViewPool {

    private val rowViewHolders = mutableMapOf<Int, RowViewHolder<*>>()

    override fun getRowViewHolder(rowId: Int): RowViewHolder<*>? {
        if (rowViewHolders.containsKey(rowId))
            return rowViewHolders[rowId]

        return null
    }

    override fun putRowViewHolder(rowId: Int, rowViewHolder: RowViewHolder<*>) {
        if (!rowViewHolders.containsKey(rowId))
            rowViewHolders.put(rowId, rowViewHolder)
    }
}