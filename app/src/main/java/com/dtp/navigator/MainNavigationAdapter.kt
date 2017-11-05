package com.dtp.navigator

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dtp.navigator.tabs.RowOneViewHolder
import com.dtp.navigator.tabs.RowThreeViewHolder
import com.dtp.navigator.tabs.RowTwoViewHolder
import com.dtp.renav.interfaces.RowHolder
import com.dtp.renav.base.SimpleNavigationAdapter
import com.dtp.renav.interfaces.NavigationContainer

/**
 * Created by ner on 7/22/17.
 */
class MainNavigationAdapter(columns: List<Column>) : SimpleNavigationAdapter(columns) {
    override fun createRowViewHolderForId(layoutInflater: LayoutInflater, container: NavigationContainer, rowId: Int): RowHolder<*> {
        return when (rowId) {
            R.layout.row_one -> {
                Log.i("MainNavigationAdapter", "Creating RowOneViewHolder")
                RowOneViewHolder(layoutInflater.inflate(rowId, container.getRootContainerView(), false) as ViewGroup)
            }
            R.layout.row_two -> {
                Log.i("MainNavigationAdapter", "Creating RowTwoViewHolder")
                RowTwoViewHolder(layoutInflater.inflate(rowId, container.getRootContainerView(), false) as ViewGroup)
            }
            R.layout.row_three -> {
                Log.i("MainNavigationAdapter", "Creating RowThreeViewHolder")
                RowThreeViewHolder(layoutInflater.inflate(rowId, container.getRootContainerView(), false) as ViewGroup)
            }
            else -> throw IllegalArgumentException("Type not supported $rowId")
        }
    }
}