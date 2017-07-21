package com.dtp.navigator

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dtp.navigator.tabs.TabOne
import com.dtp.navigator.tabs.TabThree
import com.dtp.navigator.tabs.TabTwo
import com.dtp.renav.ColumnViewHolder
import com.dtp.renav.NavigationAdapter
import com.dtp.renav.NavigationView

/**
 * Created by ner on 7/20/17.
 */
class SimpleNavigationAdapter : NavigationAdapter {

    private val columnMap = mutableMapOf(R.id.One to listOf(ColumnData(R.layout.tab_one, "Some String")),
                                         R.id.Two to listOf(ColumnData(R.layout.tab_two, "Some String")),
                                         R.id.Three to listOf(ColumnData(R.layout.tab_three, "Some String")))

    override fun getCurrentColumnViewType(columnId: Int): Int {
        if (columnMap.containsKey(columnId)) {
            if (columnMap[columnId]!!.isNotEmpty())
                return columnMap[columnId]!![0].viewId
        }

        return -1
    }

    override fun createColumnViewHolderForType(parent: ViewGroup, type: Int): ColumnViewHolder {
        when (type) {
            R.layout.tab_one -> return TabOne(LayoutInflater.from(parent.context).inflate(R.layout.tab_one, parent, false))
            R.layout.tab_two -> return TabTwo(LayoutInflater.from(parent.context).inflate(R.layout.tab_two, parent, false))
            R.layout.tab_three -> return TabThree(LayoutInflater.from(parent.context).inflate(R.layout.tab_three, parent, false))
            else -> throw IllegalArgumentException("Type not supported $type")
        }
    }

    override fun bindColumnView(column: Int, navigationView: NavigationView) {
        Log.i("SimpleNavigationAdapter", "Binding navigation adapter with value ${columnMap[column]?.get(0)}")
    }

    data class ColumnData(val viewId: Int, val value: String)
}