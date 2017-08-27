package com.dtp.navigator.rows

import com.dtp.navigator.R
import com.dtp.renav.SimpleNavigationAdapter.Row

/**
 * Created by ner on 8/27/17.
 */
class RowOne(item: Int) : Row<Int>(item) {
    override val rowId = R.layout.row_one
}