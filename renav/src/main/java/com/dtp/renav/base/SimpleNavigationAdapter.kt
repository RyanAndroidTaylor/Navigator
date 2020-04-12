package com.dtp.renav.base

import com.dtp.renav.interfaces.NavigationAdapter
import com.dtp.renav.interfaces.Screen
import java.util.*

/**
 * Created by ner on 7/22/17.
 */
abstract class SimpleNavigationAdapter(columns: List<Column>) : NavigationAdapter {

    private val columnMap: Map<Int, Column>

    init {
        val columnMap = mutableMapOf<Int, Column>()

        columns.forEach { columnMap[it.columnId] = it }

        this.columnMap = columnMap
    }

    override fun getScreenId(columnId: Int): Int {
        return columnMap[columnId]?.screens?.peek()?.screenId ?: -1
    }

    override fun bindColumnView(columnId: Int, screen: Screen<*>) {
        columnMap[columnId]?.screens?.peek()?.bind(screen)
    }

    override fun pushScreen(columnId: Int, screenData: ScreenData<*>) {
        columnMap[columnId]?.screens?.push(screenData)
    }

    override fun popScreen(columnId: Int) {
        columnMap[columnId]?.screens?.pop()
    }

    override fun handleBack(columnId: Int): Boolean {
        return columnMap[columnId]?.handleBackPressed() ?: false
    }

    class Column(val columnId: Int, rootScreenData: ScreenData<*>) {
        val screens = Stack<ScreenData<*>>()

        init {
            screens.push(rootScreenData)
        }

        open fun handleBackPressed(): Boolean {
            return if (screens.size > 1) {
                screens.pop()

                true
            } else
                false
        }
    }

    /**
     * Connects the screens data to its ID
     */
    abstract class ScreenData<T>(private val item: T) {
        abstract val screenId: Int

        @Suppress("UNCHECKED_CAST")
        fun bind(screen: Screen<*>) {
            (screen as Screen<T>).bind(item)
        }
    }
}