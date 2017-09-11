package com.dtp.navigator

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dtp.navigator.tabs.RowOneViewHolder
import com.dtp.navigator.tabs.RowThreeViewHolder
import com.dtp.navigator.tabs.RowTwoViewHolder
import com.dtp.renav.interfaces.NavigatorContainer
import com.dtp.renav.interfaces.RowHolder
import com.dtp.renav.base.SimpleNavigationAdapter

/**
 * Created by ner on 7/22/17.
 */
class MainNavigationAdapter(columns: List<Column>) : SimpleNavigationAdapter(columns) {


    override fun createRowViewHolderForId(layoutInflater: LayoutInflater, container: NavigatorContainer, rowId: Int): RowHolder<*> {
        return when (rowId) {
            R.layout.row_one -> RowOneViewHolder(layoutInflater.inflate(rowId, container.getRootContainerView(), false) as ViewGroup)
            R.layout.row_two -> RowTwoViewHolder(layoutInflater.inflate(rowId, container.getRootContainerView(), false) as ViewGroup)
            R.layout.row_three -> RowThreeViewHolder(layoutInflater.inflate(rowId, container.getRootContainerView(), false) as ViewGroup)
            else -> throw IllegalArgumentException("Type not supported $rowId")
        }
    }
}