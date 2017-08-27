package com.dtp.navigator

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dtp.navigator.tabs.RowOneViewHolder
import com.dtp.navigator.tabs.RowThreeViewHolder
import com.dtp.navigator.tabs.RowTwoViewHolder
import com.dtp.renav.RowViewHolder
import com.dtp.renav.SimpleNavigationAdapter

/**
 * Created by ner on 7/22/17.
 */
class MainNavigationAdapter(columns: List<Column>) : SimpleNavigationAdapter(columns) {


    override fun createRowViewHolderForId(parent: ViewGroup, rowId: Int): RowViewHolder<*> {
        return when (rowId) {
            R.layout.row_one -> RowOneViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_one, parent, false))
            R.layout.row_two -> RowTwoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_two, parent, false))
            R.layout.row_three -> RowThreeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_three, parent, false))
            else -> throw IllegalArgumentException("Type not supported $rowId")
        }
    }
}