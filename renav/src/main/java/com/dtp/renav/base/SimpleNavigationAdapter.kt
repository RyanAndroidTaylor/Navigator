package com.dtp.renav.base

import com.dtp.renav.interfaces.NavigationAdapter
import com.dtp.renav.interfaces.RowHolder
import java.util.*

/**
 * Created by ner on 7/22/17.
 */
abstract class SimpleNavigationAdapter(columns: List<Column>) : NavigationAdapter {

    private val columnMap: Map<Int, Column>

    init {
        val columnMap = mutableMapOf<Int, Column>()
        columns.forEach { columnMap.put(it.columnId, it) }
        this.columnMap = columnMap
    }

    override fun getRowId(columnId: Int): Int {
        return columnMap[columnId]?.rows?.peek()?.rowId ?: -1
    }

    override fun bindColumnView(columnId: Int, rowHolder: RowHolder<*>) {
        columnMap[columnId]?.rows?.peek()?.bind(rowHolder)
    }

    override fun pushRowToCurrentColumn(columnId: Int, row: Row<*>) {
        columnMap[columnId]?.rows?.push(row)
    }

    override fun popColumnRow(columnId: Int) {
        columnMap[columnId]?.rows?.pop()
    }

    override fun handleBack(columnId: Int): Boolean {
        return columnMap[columnId]?.handleBackPressed() ?: false
    }

    class Column(val columnId: Int, rootRow: Row<*>) {
        val rows = Stack<Row<*>>()

        init {
            rows.push(rootRow)
        }

        open fun handleBackPressed(): Boolean {
            return if (rows.size > 1) {
                rows.pop()

                true
            } else
                false
        }
    }

    abstract class Row<T>(private val item: T) {
        abstract val rowId: Int

        @Suppress("UNCHECKED_CAST")
        fun bind(rowHolder: RowHolder<*>) {
            (rowHolder as RowHolder<T>).bind(item)
        }
    }
}