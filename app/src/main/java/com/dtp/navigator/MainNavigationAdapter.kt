package com.dtp.navigator

import android.view.LayoutInflater
import com.dtp.navigator.tabs.RowOneViewHolder
import com.dtp.navigator.tabs.RowThreeViewHolder
import com.dtp.navigator.tabs.RowTwoViewHolder
import com.dtp.renav.interfaces.NavigatorContainer
import com.dtp.renav.interfaces.RowViewHolder
import com.dtp.renav.base.SimpleNavigationAdapter

/**
 * Created by ner on 7/22/17.
 */
class MainNavigationAdapter(columns: List<Column>) : SimpleNavigationAdapter(columns) {


    override fun createRowViewHolderForId(container: NavigatorContainer, rowId: Int): RowViewHolder<*> {
        return when (rowId) {
            R.layout.row_one -> RowOneViewHolder(LayoutInflater.from(container.getRootContainerView().context).inflate(rowId, container.getRootContainerView(), false))
            R.layout.row_two -> RowTwoViewHolder(LayoutInflater.from(container.getRootContainerView().context).inflate(rowId, container.getRootContainerView(), false))
            R.layout.row_three -> RowThreeViewHolder(LayoutInflater.from(container.getRootContainerView().context).inflate(rowId, container.getRootContainerView(), false))
            else -> throw IllegalArgumentException("Type not supported $rowId")
        }
    }
}