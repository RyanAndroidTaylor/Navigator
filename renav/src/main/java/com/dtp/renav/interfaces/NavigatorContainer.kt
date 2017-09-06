package com.dtp.renav.interfaces

import android.view.ViewGroup

/**
 * Created by ner on 9/5/17.
 */
interface NavigatorContainer {
    fun setRootContainerView(view: ViewGroup)
    fun getRootContainerView(): ViewGroup

    fun detachCurrentViewHolder()

    fun attachViewHolder(viewHolder: RowViewHolder<*>)
}