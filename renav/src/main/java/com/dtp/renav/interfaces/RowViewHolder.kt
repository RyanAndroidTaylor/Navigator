package com.dtp.renav.interfaces

import android.view.View

/**
 * Created by ner on 7/12/17.
 */
interface RowViewHolder<in T> {
    val rootView: View

    fun bind(item: T)

    fun onAttach() {}
    fun onDetach() {}
}