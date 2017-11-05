package com.dtp.navigator.tabs

import android.view.ViewGroup
import android.widget.TextView
import com.dtp.navigator.R
import com.dtp.renav.base.ViewRowHolder

/**
 * Created by ner on 7/20/17.
 */
class RowThreeViewHolder(override val rootView: ViewGroup) : ViewRowHolder<String>() {
    private val text: TextView = rootView.findViewById(R.id.textView)

    override fun bind(item: String) {
        text.text = item
    }
}