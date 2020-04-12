package com.dtp.renav.base

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.dtp.renav.interfaces.NavigationManager
import com.dtp.renav.interfaces.Screen

/**
 * Created by ner on 9/6/17.
 */
abstract class ViewScreen<in T> : Screen<T>, LifecycleOwner {

    private var navigationManager: NavigationManager? = null

    private lateinit var lifecycleRegistry: LifecycleRegistry

    abstract val rootView: ViewGroup

    protected val context: Context
        get() = rootView.context

    init {
        initLifecycle()

        //TODO Work out when ON_STOP and ON_CREATE should be called
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun getLifecycle() = lifecycleRegistry

    override fun onAttach(navigationManager: NavigationManager) {
        this.navigationManager = navigationManager
    }

    override fun onDetach() {
        navigationManager = null

        //TODO Work out when ON_STOP and ON_CREATE should be called
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    override fun onPause() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    override fun onResume() {
       lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onDestroy() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    override fun pushScreen(screenData: SimpleNavigationAdapter.ScreenData<*>) {
        navigationManager?.pushScreen(screenData)
    }

    override fun popScreen() {
        navigationManager?.popScreen()
    }

    override fun setSupportActionBar(toolbar: Toolbar) {
        navigationManager?.setSupportActionBar(toolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return false
    }

    override fun setDisplayHomeAsUpEnabled(enabled: Boolean) {
        navigationManager?.setDisplayHomeAsUpEnabled(enabled)
    }

    override fun hasPermission(permission: String): Boolean =
            navigationManager?.checkPermission(permission) ?: false

    override fun requestPermission(requestCode: Int, permissions: Array<String>) {
        navigationManager?.requestPermission(requestCode, permissions)
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        navigationManager?.startActivityForResult(intent, requestCode)
    }

    private fun initLifecycle() {
        lifecycleRegistry = LifecycleRegistry(this)
    }
}