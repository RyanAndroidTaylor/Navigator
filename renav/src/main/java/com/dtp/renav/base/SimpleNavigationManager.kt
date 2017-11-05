package com.dtp.renav.base

import android.content.Intent
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
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
    private val rowHolderStack: RowHolderStack by lazy { RowHolderStack() }

    private var currentColumnId: Int = -1

    private var currentRowHolder: RowHolder<*>? = null

    private lateinit var navigationView: NavigationView

    override var shouldRecycleViews: Boolean = true

    override fun attachNavigationView(navView: NavigationView) {
        navigationView = navView
    }

    override fun setAdapter(adapter: NavigationAdapter?) {
        this.adapter = adapter
    }

    override fun columnSelected(columnId: Int) {
        Log.i("SimpleNavigationManager", "Colum selected $columnId")
        adapter?.let { adapter ->
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
        currentRowHolder?.onDetach()

        currentRowHolder?.onDestroy()

        rowHolderPool.destroyRowHolders()
    }

    override fun pushRow(row: SimpleNavigationAdapter.Row<*>) {
        adapter?.let { adapter ->
            adapter.pushRow(currentColumnId, row)

            if (!shouldRecycleViews) {
                val layoutInflater = LayoutInflater.from(navigationView.context)

                val rowHolder = adapter.createRowViewHolderForId(layoutInflater, navigationView.container, row.rowId)

                rowHolderStack.push(currentColumnId, rowHolder)
            }

            bindCurrentColumn()
        }
    }

    override fun popRow() {
        adapter?.let { adapter ->
            adapter.popRow(currentColumnId)

            if (!shouldRecycleViews)
                rowHolderStack.pop(currentColumnId)

            bindCurrentColumn()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = currentRowHolder?.onOptionsItemSelected(item) == true

    override fun setSupportActionBar(toolbar: Toolbar) {
        navigationView.setSupportActionBar(toolbar)
    }

    override fun setDisplayHomeAsUpEnabled(enabled: Boolean) {
        navigationView.setDisplayHomeAsUpEnabled(enabled)
    }

    override fun checkPermission(permission: String): Boolean =
            navigationView.checkPermission(permission)

    override fun requestPermission(requestCode: Int, permissions: Array<String>) =
            navigationView.requestPermission(requestCode, permissions)


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        currentRowHolder?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        navigationView.startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        currentRowHolder?.onActivityResult(requestCode, resultCode, data)
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

            currentRowHolder?.let { rowViewHolder ->

                currentRowHolder?.onPause()

                currentRowHolder?.onDetach()

                navigationView.detachCurrentRowViewHolder()

                if (shouldRecycleViews)
                    rowHolderPool.putRowViewHolder(adapter.getRowId(currentColumnId), rowViewHolder)
            }

            val viewHolder: RowHolder<*> = if (shouldRecycleViews) {
                rowHolderPool.getRowViewHolder(rowId) ?: let {
                    val layoutInflater = LayoutInflater.from(navigationView.context)

                    adapter.createRowViewHolderForId(layoutInflater, navigationView.container, rowId)
                }
            } else {
                rowHolderStack.peek(currentColumnId) ?: let {
                    val layoutInflater = LayoutInflater.from(navigationView.context)

                    val rowHolder = adapter.createRowViewHolderForId(layoutInflater, navigationView.container, rowId)

                    rowHolderStack.push(currentColumnId, rowHolder)

                    rowHolder
                }
            }

            currentRowHolder = viewHolder

            navigationView.attachRowViewHolder(viewHolder)

            currentRowHolder?.onResume()

            currentRowHolder?.onAttach(this)

            adapter.bindColumnView(currentColumnId, viewHolder)
        }
    }
}