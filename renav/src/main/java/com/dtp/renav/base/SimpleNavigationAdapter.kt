package com.dtp.renav.base

import com.dtp.renav.interfaces.NavigationAdapter
import com.dtp.renav.interfaces.RowViewHolder
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

    abstract class Row<T>(private val item: T) {
        abstract val rowId: Int

        @Suppress("UNCHECKED_CAST")
        fun bind(rowViewHolder: RowViewHolder<*>) {
            (rowViewHolder as RowViewHolder<T>).bind(item)
        }
    }
}