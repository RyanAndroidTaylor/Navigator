package com.dtp.renav

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

    override fun bindColumnView(columnId: Int, rowViewHolder: RowViewHolder<*>) {
        columnMap[columnId]?.rows?.peek()?.bind(rowViewHolder)
    }

    override fun handleBack(columnId: Int): Boolean {
        return columnMap[columnId]?.rows?.let { rows ->
            if (rows.size > 1) {
                rows.pop()

                true
            } else {
                false
            }
        } ?: false
    }

    class Column(val columnId: Int, vararg rows: Row<*>) {
        val rows = Stack<Row<*>>()

        init {
            rows.forEach { this.rows.push(it) }
        }
    }

    class Row<T>(val rowId: Int, private val item: T) {

        @Suppress("UNCHECKED_CAST")
        fun bind(rowViewHolder: RowViewHolder<*>) {
            (rowViewHolder as RowViewHolder<T>).bind(item)
        }
    }
}