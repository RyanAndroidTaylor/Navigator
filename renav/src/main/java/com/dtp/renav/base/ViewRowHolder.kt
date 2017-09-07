package com.dtp.renav.base

import android.view.ViewGroup
import com.dtp.renav.interfaces.RowHolder

/**
 * Created by ner on 9/6/17.
 */
interface ViewRowHolder<in T> : RowHolder<T> {
    val rootView: ViewGroup
}