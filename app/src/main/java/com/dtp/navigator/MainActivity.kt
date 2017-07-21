package com.dtp.navigator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dtp.renav.BasicNavigationManager
import com.dtp.renav.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navView = findViewById<NavigationView>(R.id.nav_view)

        val manager = BasicNavigationManager(SimpleNavigationAdapter())
        navView.navigationManager = manager
    }
}
