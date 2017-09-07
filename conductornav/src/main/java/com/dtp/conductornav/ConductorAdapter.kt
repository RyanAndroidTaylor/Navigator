package com.dtp.conductornav

import com.dtp.conductornav.row_holders.RowOneHolder
import com.dtp.conductornav.row_holders.RowTwoHolder
import com.dtp.renav.base.SimpleNavigationAdapter
import com.dtp.renav.interfaces.NavigatorContainer
import com.dtp.renav.interfaces.RowHolder

/**
 * Created by ner on 9/7/17.
 */
class ConductorAdapter(columns: List<Column>) : SimpleNavigationAdapter(columns) {

    override fun createRowViewHolderForId(container: NavigatorContainer, rowId: Int): RowHolder<*> {
        return when (rowId) {
            R.layout.controller_one -> RowOneHolder()
            R.layout.controller_two -> RowTwoHolder()
            else -> throw IllegalArgumentException("Now RowHolder found for row Id")
        }
    }
}