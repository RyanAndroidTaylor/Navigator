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
            currentRowViewHolder?.let { rowViewHolder ->
                currentRowViewHolder = null

                viewPool.putRowViewHolder(adapter.getRowId(currentColumnId), rowViewHolder)
            }

            currentColumnId = columnId

            bindCurrentColumn()
        } ?: Log.i("BasicNavigationManager", "Column selected but no adapter was found.")
    }

    override fun handleBack(): Boolean {
        return adapter?.let { adapter ->
            if (adapter.handleBack(currentColumnId)) {
                bindCurrentColumn()

                true
            } else {
                false
            }
        } ?: false
    }

    private fun bindCurrentColumn() {
        adapter?.let { adapter ->
            val rowId = adapter.getRowId(currentColumnId)
            Log.i("BasicNavigationAdapter", "RowId $rowId")

            val viewHolder = viewPool.getRowViewHolder(rowId) ?: adapter.createRowViewHolderForId(navigationView.container, rowId)

            currentRowViewHolder = viewHolder

            navigationView.attachColumnView(viewHolder.rootView)

            adapter.bindColumnView(currentColumnId, viewHolder)
        }
    }
}