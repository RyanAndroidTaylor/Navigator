package com.dtp.conductornav.row_holders

import com.bluelinelabs.conductor.Controller
import com.dtp.conductornav.controllers.OneController
import com.dtp.renav.conductor.ConductorRowHolder

/**
 * Created by ner on 9/7/17.
 */
class RowOneHolder : ConductorRowHolder<String> {

    private lateinit var controller: OneController

    override fun createController(): Controller {
        controller = OneController()

        return controller
    }

    override fun bind(item: String) {
        controller.setText(item)
    }
}