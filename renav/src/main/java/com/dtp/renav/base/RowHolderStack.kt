package com.dtp.renav.base

import com.dtp.renav.interfaces.RowHolder
import java.util.*

/**
 * Created by ner on 10/22/17.
 */
class RowHolderStack {

    private val rowHolderMap = mutableMapOf<Int, Stack<RowHolder<*>>>()

    fun push(columnId: Int, rowHolder: RowHolder<*>) {
        if (!rowHolderMap.containsKey(columnId))
            rowHolderMap.put(columnId, Stack())

        rowHolderMap[columnId]!!.push(rowHolder)
    }

    fun pop(columnId: Int): RowHolder<*>? {
        return rowHolderMap[columnId]!!.pop()
    }

    fun peek(columnId: Int): RowHolder<*>? {
        return rowHolderMap[columnId]?.peek()
    }
}