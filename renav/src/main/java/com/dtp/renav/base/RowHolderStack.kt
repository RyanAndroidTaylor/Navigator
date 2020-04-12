package com.dtp.renav.base

import com.dtp.renav.interfaces.Screen
import java.util.*

/**
 * Created by ner on 10/22/17.
 */
class RowHolderStack {

    private val rowHolderMap = mutableMapOf<Int, Stack<Screen<*>>>()

    fun push(columnId: Int, screen: Screen<*>) {
        if (!rowHolderMap.containsKey(columnId))
            rowHolderMap[columnId] = Stack()

        rowHolderMap[columnId]!!.push(screen)
    }

    fun pop(columnId: Int): Screen<*>? {
        return rowHolderMap[columnId]!!.pop()
    }

    fun peek(columnId: Int): Screen<*>? {
        return rowHolderMap[columnId]?.peek()
    }
}