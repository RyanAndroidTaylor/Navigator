package com.dtp.renav.base

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.dtp.renav.NavigationView
import com.dtp.renav.interfaces.NavigationAdapter
import com.dtp.renav.interfaces.NavigationManager
import com.dtp.renav.interfaces.Screen
import com.dtp.renav.interfaces.RowHolderPool

/**
 * Created by ner on 7/14/17.
 */
//TODO Make lifecycle aware so you don't have to call all the lifecycle methods in the activity
class SimpleNavigationManager(private var adapter: NavigationAdapter? = null) : NavigationManager {

    private val rowHolderPool: RowHolderPool by lazy { SimpleRowHolderPool() }
    private val rowHolderStack: RowHolderStack by lazy { RowHolderStack() }

    private var currentColumnId: Int = -1

    private var currentScreen: Screen<*>? = null

    private lateinit var navigationView: NavigationView

    override var activity: Activity? = null

    override var shouldRecycleViews: Boolean = true

    override fun attachNavigationView(navView: NavigationView) {
        navigationView = navView
    }

    override fun setAdapter(adapter: NavigationAdapter?) {
        this.adapter = adapter
    }

    override fun columnSelected(columnId: Int) {
        adapter?.let { adapter ->
            unbindCurrentColumn()

            currentColumnId = columnId

            // The first time a column is selected the RowHolder has not been created for it yet
            if (rowHolderStack.peek(currentColumnId) == null)
                rowHolderStack.push(currentColumnId, createRowHolder(adapter, adapter.getScreenId(currentColumnId)))

            bindCurrentColumn()
        } ?: Log.i("BasicNavigationManager", "Column selected but no adapter was found.")
    }

    override fun onResume() {
        currentScreen?.onResume()
    }

    override fun onPause() {
        currentScreen?.onPause()
    }

    override fun onDestroy() {
        currentScreen?.onDetach()

        currentScreen?.onDestroy()

        rowHolderPool.destroyRowHolders()
    }

    override fun pushScreen(screenData: SimpleNavigationAdapter.ScreenData<*>) {
        adapter?.let { adapter ->
            unbindCurrentColumn()

            adapter.pushScreen(currentColumnId, screenData)

            rowHolderStack.push(currentColumnId, createRowHolder(adapter, screenData.screenId))

            bindCurrentColumn()
        }
    }

    override fun popScreen() {
        adapter?.let { adapter ->
            unbindCurrentColumn()

            adapter.popScreen(currentColumnId)

            rowHolderStack.pop(currentColumnId)

            bindCurrentColumn()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = currentScreen?.onOptionsItemSelected(item) == true

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
        currentScreen?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        navigationView.startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        currentScreen?.onActivityResult(requestCode, resultCode, data)
    }

    override fun handleBack(): Boolean {
        return adapter?.let { adapter ->
            if (adapter.handleBack(currentColumnId)) {
                unbindCurrentColumn()

                rowHolderStack.push(currentColumnId, createRowHolder(adapter, adapter.getScreenId(currentColumnId)))

                bindCurrentColumn()

                true
            } else {
                false
            }
        } ?: false
    }

    private fun createRowHolder(adapter: NavigationAdapter, rowId: Int): Screen<*> {
        return if (shouldRecycleViews) {
            rowHolderPool.getRowViewHolder(rowId) ?: let {
                val layoutInflater = LayoutInflater.from(navigationView.context)

                val rowHolder = adapter.createRowViewHolderForId(layoutInflater, navigationView.container, rowId)

                rowHolderPool.putRowViewHolder(rowId, rowHolder)

                rowHolder
            }
        } else {
            rowHolderStack.peek(currentColumnId) ?: let {
                val layoutInflater = LayoutInflater.from(navigationView.context)

                adapter.createRowViewHolderForId(layoutInflater, navigationView.container, rowId)
            }
        }
    }

    private fun unbindCurrentColumn() {
        adapter?.let { adapter ->
            currentScreen?.let { rowViewHolder ->

                currentScreen?.onPause()

                currentScreen?.onDetach()

                navigationView.detachCurrentRowViewHolder()

                if (shouldRecycleViews)
                    rowHolderPool.putRowViewHolder(adapter.getScreenId(currentColumnId), rowViewHolder)
            }
        }
    }

    private fun bindCurrentColumn() {
        adapter?.let { adapter ->
            rowHolderStack.peek(currentColumnId)?.let { viewHolder ->
                currentScreen = viewHolder

                navigationView.attachRowViewHolder(viewHolder)

                currentScreen?.onAttach(activity, this)

                currentScreen?.onResume()

                adapter.bindColumnView(currentColumnId, viewHolder)
            }
        }
    }
}