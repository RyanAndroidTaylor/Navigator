package com.dtp.navigator

import android.app.Application
import com.dtp.renav.NavigationView

/**
 * Created by ner on 11/4/17.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        NavigationView.shouldRecycleViews = false
    }
}