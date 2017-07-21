package com.dtp.renav.base

import com.dtp.renav.ColumnViewHolder
import com.dtp.renav.ColumnViewPool

/**
 * Created by ner on 7/15/17.
 */
class BaseColumnViewPool : ColumnViewPool {

    private val columnViewHolders = mutableMapOf<Int, ColumnViewHolder>()

    override fun getView(type: Int): ColumnViewHolder? {
        if (columnViewHolders.containsKey(type))
            return columnViewHolders[type]

        return null
    }

    override fun addView(type: Int, columnViewHolder: ColumnViewHolder) {
        if (!columnViewHolders.containsKey(type))
            columnViewHolders.put(type, columnViewHolder)
    }
}