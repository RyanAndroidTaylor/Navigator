package com.dtp.conductorrenav

import android.view.ViewGroup
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.dtp.renav.interfaces.NavigatorContainer
import com.dtp.renav.interfaces.RowHolder

class ConductorNavigatorContainer : NavigatorContainer {

    private var router: Router? = null

    private lateinit var rootContainerView: ViewGroup

    override fun getRootContainerView(): ViewGroup = rootContainerView

    override fun setRootContainerView(view: ViewGroup) {
        rootContainerView = view
    }

    override fun attachViewHolder(holder: RowHolder<*>) {
        if (holder is ConductorRowHolder<*>) {
            router?.setRoot(RouterTransaction.with(holder.controller))
        } else {
            throw IllegalStateException("Trying to attach RowViewHolder that is not type of ConductorRowViewHolder")
        }
    }

    override fun detachCurrentViewHolder() {

    }

    fun attach(router: Router) {
        this.router = router
    }

    fun detach() {
        router = null
    }
}