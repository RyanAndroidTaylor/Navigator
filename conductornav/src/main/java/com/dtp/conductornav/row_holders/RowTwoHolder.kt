package com.dtp.conductornav.row_holders

import com.bluelinelabs.conductor.Controller
import com.dtp.conductornav.controllers.TwoController
import com.dtp.renav.conductor.ConductorRowHolder

/**
 * Created by ner on 9/7/17.
 */
class RowTwoHolder : ConductorRowHolder<Long> {
    private lateinit var controller: TwoController

    override fun createController(): Controller {
        controller = TwoController()

        return controller
    }

    override fun bind(item: Long) {
        controller.setNumber(item)
    }

}