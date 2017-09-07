package com.dtp.conductornav

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router

/**
 * Created by ner on 8/29/17.
 */
abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutId: Int
    abstract val containerId: Int

    protected lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        router = Conductor.attachRouter(this, findViewById(containerId), savedInstanceState)

        onPostCreate()
    }

    override fun onBackPressed() {
        if (!router.handleBack())
            super.onBackPressed()
    }

    abstract fun onPostCreate()
}