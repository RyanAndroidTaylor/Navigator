package com.dtp.conductornav.controllers

import android.view.View
import android.widget.TextView
import com.bluelinelabs.conductor.Controller
import com.dtp.conductornav.BaseController
import com.dtp.conductornav.R

/**
 * Created by ner on 9/6/17.
 */
class OneController : BaseController() {
    override val layoutId = R.layout.controller_one

    private lateinit var textView: TextView

    override fun postCreateView(controller: Controller, view: View) {
        super.postCreateView(controller, view)

        textView = view.findViewById(R.id.one_text)
    }

    fun setText(text: String) {
        textView.text = text
    }
}