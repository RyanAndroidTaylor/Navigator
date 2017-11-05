package com.dtp.navigator.tabs

import android.view.ViewGroup
import android.widget.TextView
import com.dtp.navigator.R
import com.dtp.renav.base.ViewRowHolder
import com.dtp.renav.interfaces.RowHolder

/**
 * Created by ner on 7/20/17.
 */
class RowTwoViewHolder(override val rootView: ViewGroup) : ViewRowHolder<Long>() {

    private val text: TextView = rootView.findViewById(R.id.textView)

    override fun bind(item: Long) {
        text.text = item.toString()
    }
}