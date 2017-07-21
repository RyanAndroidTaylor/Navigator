package com.dtp.renav

/**
 * Created by ner on 7/12/17.
 */
interface ColumnViewPool {

    fun getView(type: Int): ColumnViewHolder?
    fun addView(type: Int, columnViewHolder: ColumnViewHolder)
}