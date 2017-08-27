package com.dtp.navigator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dtp.renav.BasicNavigationManager
import com.dtp.renav.NavigationView
import com.dtp.renav.SimpleNavigationAdapter.Row
import com.dtp.renav.SimpleNavigationAdapter.Column

class MainActivity : AppCompatActivity() {

    private lateinit var navView: NavigationView

    private val columns = listOf(Column(R.id.One, Row(R.layout.row_two, 2L), Row(R.layout.row_three, "Three"), Row(R.layout.row_one, 1)),
                                 Column(R.id.Two, Row(R.layout.row_two, 2L), Row(R.layout.row_three, "Three"), Row(R.layout.row_one, 1)),
                                 Column(R.id.Three, Row(R.layout.row_three, "Three"), Row(R.layout.row_one, 1), Row(R.layout.row_two, 2L)))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navView = findViewById(R.id.nav_view)

        val manager = BasicNavigationManager(MainNavigationAdapter(columns))
        navView.navigationManager = manager
    }

    override fun onBackPressed() {
        if (!navView.handleBack())
            super.onBackPressed()
    }
}
