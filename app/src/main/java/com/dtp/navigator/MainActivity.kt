package com.dtp.navigator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dtp.navigator.rows.RowOne
import com.dtp.navigator.rows.RowThree
import com.dtp.navigator.rows.RowTwo
import com.dtp.renav.NavigationView
import com.dtp.renav.base.SimpleNavigationAdapter.Column
import com.dtp.renav.base.SimpleNavigationManager

class MainActivity : AppCompatActivity() {

    private lateinit var navView: NavigationView

    private val columns = listOf(Column(R.id.One, RowOne(1)),
                                 Column(R.id.Two, RowTwo(2L)),
                                 Column(R.id.Three, RowThree("Three")))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navView = findViewById(R.id.nav_view)

        val manager = SimpleNavigationManager(MainNavigationAdapter(columns))
        navView.navigationManager = manager
    }

    override fun onBackPressed() {
        if (!navView.handleBack())
            super.onBackPressed()
    }
}
