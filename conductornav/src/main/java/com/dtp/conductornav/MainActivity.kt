package com.dtp.conductornav

import com.dtp.conductornav.rows.RowOne
import com.dtp.conductornav.rows.RowTwo
import com.dtp.renav.NavigationView
import com.dtp.renav.base.BasicNavigationManager
import com.dtp.renav.base.SimpleNavigationAdapter
import com.dtp.renav.conductor.ConductorNavigatorContainer

class MainActivity : BaseActivity() {
    override val layoutId = R.layout.activity_main
    override val containerId = R.id.container

    private lateinit var navigationView: NavigationView

    private val container = ConductorNavigatorContainer()

    private val columns = listOf(SimpleNavigationAdapter.Column(R.id.One, RowOne("Test"), RowTwo(1203012), RowOne("Other")),
                                 SimpleNavigationAdapter.Column(R.id.Two, RowOne("Test"), RowTwo(1203012), RowOne("Other")),
                                 SimpleNavigationAdapter.Column(R.id.Three, RowOne("Test"), RowTwo(1203012), RowOne("Other")))

    override fun onPostCreate() {
        navigationView = findViewById(R.id.navigation_view)

        navigationView.attachContainer(container)

        navigationView.navigationManager = BasicNavigationManager().apply { setAdapter(ConductorAdapter(columns)) }
    }

    override fun onBackPressed() {
        if (!navigationView.handleBack())
            super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()

        container.attach(router)
    }

    override fun onPause() {
        super.onPause()

        container.detach()
    }
}
