package com.dtp.renav

import android.view.ViewGroup

/**
 * Created by ner on 7/12/17.
 */
interface NavigationAdapter {

    fun getCurrentColumnViewType(columnId: Int): Int
    fun createColumnViewHolderForType(parent: ViewGroup, type: Int): ColumnViewHolder
    fun bindColumnView(column: Int, navigationView: NavigationView)
}