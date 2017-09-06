package com.dtp.renav.base

import android.util.Log
import com.dtp.renav.*
import com.dtp.renav.interfaces.ColumnViewPool
import com.dtp.renav.interfaces.NavigationAdapter
import com.dtp.renav.interfaces.NavigationManager
import com.dtp.renav.interfaces.RowViewHolder

/**
 * Created by ner on 7/14/17.
 */
class BasicNavigationManager(private var adapter: NavigationAdapter? = null) : NavigationManager {

    private val viewPool: ColumnViewPool by lazy { BaseColumnViewPool() }

    private var currentColumnId: Int = -1

    private var currentRowViewHolder: RowViewHolder<*>? = null

    private lateinit var navigationView: NavigationView

    override fun attachNavigationView(navView: NavigationView) {
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
            val viewHolder = viewPool.getRowViewHolder(rowId) ?: adapter.createRowViewHolderForId(navigationView.container, rowId)

            navigationView.detachCurrentRowViewHolder()

            currentRowViewHolder?.onDetach()

            currentRowViewHolder = viewHolder

            navigationView.attachRowViewHolder(viewHolder)

            currentRowViewHolder?.onAttach()

            adapter.bindColumnView(currentColumnId, viewHolder)
        }
    }
}