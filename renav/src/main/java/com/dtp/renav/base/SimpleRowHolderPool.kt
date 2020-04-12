package com.dtp.renav.base

import com.dtp.renav.interfaces.Screen
import com.dtp.renav.interfaces.RowHolderPool

/**
 * Created by ner on 7/15/17.
 */
class SimpleRowHolderPool : RowHolderPool {

    private val rowViewHolders = mutableMapOf<Int, Screen<*>>()

    override fun getRowViewHolder(rowId: Int): Screen<*>? {
        if (rowViewHolders.containsKey(rowId))
            return rowViewHolders[rowId]

        return null
    }

    override fun putRowViewHolder(rowId: Int, screen: Screen<*>) {
        if (!rowViewHolders.containsKey(rowId))
            rowViewHolders[rowId] = screen
    }

    override fun destroyRowHolders() {
        rowViewHolders.values.forEach { it.onDestroy() }

        rowViewHolders.clear()
    }
}