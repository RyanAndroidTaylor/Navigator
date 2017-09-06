package com.dtp.navigator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dtp.navigator.rows.RowOne
import com.dtp.navigator.rows.RowThree
import com.dtp.navigator.rows.RowTwo
import com.dtp.renav.base.BasicNavigationManager
import com.dtp.renav.NavigationView
import com.dtp.renav.base.DefaultNavigatorContainer
import com.dtp.renav.base.SimpleNavigationAdapter.Row
import com.dtp.renav.base.SimpleNavigationAdapter.Column

class MainActivity : AppCompatActivity() {

    private lateinit var navView: NavigationView

    private val columns = listOf(Column(R.id.One, RowTwo(2L), RowThree("Three"), RowOne(1)),
                                 Column(R.id.Two, RowTwo(2L), RowThree("Three"), RowOne(1)),
                                 Column(R.id.Three, RowThree("Three"), RowOne(1), RowTwo(2L)))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navView = findViewById(R.id.nav_view)

        navView.attachContainer(DefaultNavigatorContainer())

        val manager = BasicNavigationManager(MainNavigationAdapter(columns))
        navView.navigationManager = manager
    }

    override fun onBackPressed() {
        if (!navView.handleBack())
            super.onBackPressed()
    }
}
