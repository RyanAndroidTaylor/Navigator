package com.dtp.navigator

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dtp.navigator.tabs.RowOneViewHolder
import com.dtp.navigator.tabs.RowThreeViewHolder
import com.dtp.navigator.tabs.RowTwoViewHolder
import com.dtp.renav.RowViewHolder
import com.dtp.renav.NavigationAdapter

/**
 * Created by ner on 7/20/17.
 */
class SimpleNavigationAdapter : NavigationAdapter {

    val columnOne = Column(R.id.One, mutableListOf(R.layout.row_one, R.layout.row_three, R.layout.row_one))
    val columnTwo = Column(R.id.Two, mutableListOf(R.layout.row_two, R.layout.row_three, R.layout.row_one))
    val columnThree = Column(R.id.Three, mutableListOf(R.layout.row_three, R.layout.row_two, R.layout.row_one))

    private val rowMap = mutableMapOf(R.layout.row_one to Row(R.layout.row_one, mutableListOf(12431, 429, 915)),
                                      R.layout.row_two to Row(R.layout.row_two, mutableListOf(90123, 8194, 1)),
                                      R.layout.row_three to Row(R.layout.row_three, mutableListOf("One", "Two", "Three")))

    val columns = mapOf(R.id.One to columnOne,
                        R.id.Two to columnTwo,
                        R.id.Three to columnThree)

    override fun getRowId(columnId: Int): Int {
        return columns[columnId]?.rows?.get(0) ?: -1
    }

    override fun createRowViewHolderForId(parent: ViewGroup, rowId: Int): RowViewHolder<*> {
        when (rowId) {
            R.layout.row_one -> return RowOneViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_one, parent, false))
            R.layout.row_two -> return RowTwoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_two, parent, false))
            R.layout.row_three -> return RowThreeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_three, parent, false))
            else -> throw IllegalArgumentException("Type not supported $rowId")
        }
    }

    override fun bindColumnView(columnId: Int, rowViewHolder: RowViewHolder<*>) {
        val rowId = getRowId(columnId)

        rowMap[rowId]?.bind(rowViewHolder)
    }

    data class Column(val columnId: Int, val rows: MutableList<Int>)

    class Row<T>(val rowId: Int, val items: MutableList<T>) {

        @Suppress("UNCHECKED_CAST")
        fun bind(rowViewHolder: RowViewHolder<*>) {
            (rowViewHolder as RowViewHolder<T>).bind(items[items.size - 1])
        }
    }
}