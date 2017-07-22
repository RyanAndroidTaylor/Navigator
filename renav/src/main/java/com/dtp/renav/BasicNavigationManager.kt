package com.dtp.renav

import android.util.Log
import com.dtp.renav.base.BaseColumnViewPool

/**
 * Created by ner on 7/14/17.
 */
class BasicNavigationManager(private var adapter: NavigationAdapter? = null) : NavigationManager {

    private val viewPool: ColumnViewPool by lazy { BaseColumnViewPool() }

    private var currentColumnId: Int = -1

    private var currentRowViewHolder: RowViewHolder<*>? = null

    private lateinit var navigationView: NavigationView

    override fun attachNavView(navView: NavigationView) {
        navigationView = navView
    }

    override fun setAdapter(adapter: NavigationAdapter?) {
        this.adapter = adapter
    }

    override fun columnSelected(columnId: Int) {
        adapter?.let { adapter ->
            currentRowViewHolder?.let {
                val rowViewHolder = it

                currentRowViewHolder = null

                viewPool.putRowViewHolder(adapter.getRowId(currentColumnId), rowViewHolder)
            }

            currentColumnId = columnId

            val rowId = adapter.getRowId(columnId)

            val loadedViewHolder = viewPool.getRowViewHolder(rowId) ?: adapter.createRowViewHolderForId(navigationView.container, rowId)

            currentRowViewHolder = loadedViewHolder

            navigationView.attachColumnView(loadedViewHolder.rootView)

            adapter.bindColumnView(columnId, loadedViewHolder)
        } ?: Log.i("BasicNavigationManager", "Column selected but no adapter was found.")
    }
}