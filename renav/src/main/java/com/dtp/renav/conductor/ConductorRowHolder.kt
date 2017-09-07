package com.dtp.renav.conductor

import com.bluelinelabs.conductor.Controller
import com.dtp.renav.interfaces.RowHolder

/**
 * Created by ner on 9/6/17.
 */
interface ConductorRowHolder<in T> : RowHolder<T> {
    fun createController(): Controller
}