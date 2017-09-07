package com.dtp.navigator.tabs

import android.view.ViewGroup
import android.widget.TextView
import com.dtp.navigator.R
import com.dtp.renav.base.ViewRowHolder

/**
 * Created by ner on 7/20/17.
 */
class RowOneViewHolder(override val rootView: ViewGroup) : ViewRowHolder<Int> {

    private val text: TextView = rootView.findViewById(R.id.textView)

    override fun bind(item: Int) {
        text.text = item.toString()
    }
}