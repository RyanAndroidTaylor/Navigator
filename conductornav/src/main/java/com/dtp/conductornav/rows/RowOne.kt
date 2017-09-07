package com.dtp.conductornav.rows

import com.dtp.conductornav.R
import com.dtp.renav.base.SimpleNavigationAdapter

/**
 * Created by ner on 9/7/17.
 */
class RowOne(item: String) : SimpleNavigationAdapter.Row<String>(item) {
    override val rowId = R.layout.controller_one
}