package com.dtp.conductornav

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.dtp.conductornav.BaseActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by ner on 9/1/17.
 */
abstract class BaseController : Controller() {
    abstract val layoutId: Int

    private val compositeDisposable = CompositeDisposable()

    var baseActivity: BaseActivity? = null
        private set

    init {
        retainViewMode = RetainViewMode.RETAIN_DETACH

        addLifecycleListener(object : LifecycleListener() {
            override fun postCreateView(controller: Controller, view: View) {
                this@BaseController.postCreateView(controller, view)
            }

            override fun postAttach(controller: Controller, view: View) {
                this@BaseController.postAttach(controller, view)
            }

            override fun preDetach(controller: Controller, view: View) {
                this@BaseController.preDetach(controller, view)
            }

            override fun postDetach(controller: Controller, view: View) {
                baseActivity = null

                super.postDetach(controller, view)
            }

            override fun preCreateView(controller: Controller) {
                baseActivity = activity as BaseActivity

                this@BaseController.preCreateView(controller)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View =
            inflater.inflate(layoutId, container, false)

    override fun onDetach(view: View) {
        compositeDisposable.clear()

        super.onDetach(view)
    }

    open fun preCreateView(controller: Controller) {

    }

    open fun postCreateView(controller: Controller, view: View) {

    }

    open fun postAttach(controller: Controller, view: View) {

    }

    open fun preDetach(controller: Controller, view: View) {
        compositeDisposable.clear()
    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}