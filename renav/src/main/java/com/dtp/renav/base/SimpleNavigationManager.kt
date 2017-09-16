package com.dtp.renav.base

import android.util.Log
import android.view.LayoutInflater
import com.dtp.renav.NavigationView
import com.dtp.renav.interfaces.NavigationAdapter
import com.dtp.renav.interfaces.NavigationManager
import com.dtp.renav.interfaces.RowHolder
import com.dtp.renav.interfaces.RowHolderPool

/**
 * Created by ner on 7/14/17.
 */
class SimpleNavigationManager(private var adapter: NavigationAdapter? = null) : NavigationManager {

    private val rowHolderPool: RowHolderPool by lazy { SimpleRowHolderPool() }

    private var currentColumnId: Int = -1

    private var currentRowHolder: RowHolder<*>? = null

    private lateinit var navigationView: NavigationView

    override fun attachNavigationView(navView: NavigationView) {
        navigationView = navView
    }

    override fun setAdapter(adapter: NavigationAdapter?) {
        this.adapter = adapter
    }

    override fun columnSelected(columnId: Int) {
        adapter?.let { adapter ->
            currentRowHolder?.let { rowViewHolder ->
                currentRowHolder = null

                rowHolderPool.putRowViewHolder(adapter.getRowId(currentColumnId), rowViewHolder)
            }

            currentColumnId = columnId

            bindCurrentColumn()
        } ?: Log.i("BasicNavigationManager", "Column selected but no adapter was found.")
    }

    override fun onResume() {
        currentRowHolder?.onResume()
    }

    override fun onPause() {
        currentRowHolder?.onPause()
    }

    override fun onDestroy() {
        currentRowHolder?.onDestroy()

        rowHolderPool.destroyRowHolders()
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

            val viewHolder = rowHolderPool.getRowViewHolder(rowId) ?: let {
                val layoutInflater = LayoutInflater.from(navigationView.context)

                adapter.createRowViewHolderForId(layoutInflater, navigationView.container, rowId)
            }

            currentRowHolder?.onPause()

            navigationView.detachCurrentRowViewHolder()

            currentRowHolder?.onDetach()

            currentRowHolder = viewHolder

            navigationView.attachRowViewHolder(viewHolder)

            currentRowHolder?.onResume()

            currentRowHolder?.onAttach()

            adapter.bindColumnView(currentColumnId, viewHolder)
        }
    }
}