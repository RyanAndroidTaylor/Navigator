package com.dtp.conductornav.rows

import com.dtp.conductornav.R
import com.dtp.renav.base.SimpleNavigationAdapter

/**
 * Created by ner on 9/7/17.
 */
class RowTwo(item: Long) : SimpleNavigationAdapter.Row<Long>(item) {
    override val rowId = R.layout.controller_two
}