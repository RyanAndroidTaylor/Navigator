package com.dtp.renav.interfaces

/**
 * Created by ner on 7/12/17.
 */
interface RowHolder<in T> {
    fun bind(item: T)

    fun onAttach() {}
    fun onDetach() {}

    fun onResume() {}
    fun onPause() {}
    fun onDestroy() {}
}