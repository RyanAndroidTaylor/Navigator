package com.dtp.renav.base

import com.dtp.renav.interfaces.RowHolder
import com.dtp.renav.interfaces.ColumnViewPool

/**
 * Created by ner on 7/15/17.
 */
class BaseColumnViewPool : ColumnViewPool {

    private val rowViewHolders = mutableMapOf<Int, RowHolder<*>>()

    override fun getRowViewHolder(rowId: Int): RowHolder<*>? {
        if (rowViewHolders.containsKey(rowId))
            return rowViewHolders[rowId]

        return null
    }

    override fun putRowViewHolder(rowId: Int, rowHolder: RowHolder<*>) {
        if (!rowViewHolders.containsKey(rowId))
            rowViewHolders.put(rowId, rowHolder)
    }
}