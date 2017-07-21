package com.dtp.renav

import android.util.Log
import com.dtp.renav.base.BaseColumnViewPool

/**
 * Created by ner on 7/14/17.
 */
class BasicNavigationManager(private var adapter: NavigationAdapter? = null) : NavigationManager {

    private val viewPool: ColumnViewPool by lazy { BaseColumnViewPool() }

    private var currentColumnId: Int = -1

    private var currentColumnViewHolder: ColumnViewHolder? = null

    private lateinit var navigationView: NavigationView

    override fun attachNavView(navView: NavigationView) {
        navigationView = navView
    }

    override fun setAdapter(adapter: NavigationAdapter?) {
        this.adapter = adapter
    }

    override fun columnSelected(columnId: Int) {
        adapter?.let { adapter ->
            currentColumnViewHolder?.let {
                val columnViewHolder = it

                currentColumnViewHolder = null

                viewPool.addView(adapter.getCurrentColumnViewType(currentColumnId), columnViewHolder)
            }

            currentColumnId = columnId

            val viewType = adapter.getCurrentColumnViewType(columnId)

            viewPool.getView(viewType) ?: adapter.createColumnViewHolderForType(navigationView.container, viewType).let { columnViewHolder ->
                currentColumnViewHolder = columnViewHolder

                navigationView.attachColumnView(columnViewHolder.rootView)
            }
        } ?: Log.i("BasicNavigationManager", "Column selected but no adapter was found.")
    }
}