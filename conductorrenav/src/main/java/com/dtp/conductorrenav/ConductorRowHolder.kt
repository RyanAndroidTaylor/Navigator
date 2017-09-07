package com.dtp.conductorrenav

import com.bluelinelabs.conductor.Controller
import com.dtp.renav.interfaces.RowHolder

/**
 * Created by ner on 9/6/17.
 */
interface ConductorRowHolder<in T> : RowHolder<T> {
    val controller: Controller
}