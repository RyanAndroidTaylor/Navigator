package com.dtp.renav.base

import android.view.ViewGroup
import com.dtp.renav.interfaces.NavigationContainer
import com.dtp.renav.interfaces.Screen

/**
 * Created by ner on 9/5/17.
 */
class ViewNavigationContainer : NavigationContainer {
    private lateinit var rootContainerView: ViewGroup

    override fun setRootContainerView(view: ViewGroup) {
        rootContainerView = view
    }

    override fun getRootContainerView(): ViewGroup {
        return rootContainerView
    }

    override fun detachCurrentViewHolder() {
        rootContainerView.removeAllViews()
    }

    override fun attachViewHolder(holder: Screen<*>) {
        if (holder is ViewScreen<*>) {
            rootContainerView.addView(holder.rootView)
        } else {
            throw IllegalArgumentException("RowHolder is not of type ViewRowHolder")
        }
    }
}