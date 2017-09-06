package com.dtp.navigator.tabs

import android.view.View
import android.widget.TextView
import com.dtp.navigator.R
import com.dtp.renav.interfaces.RowViewHolder

/**
 * Created by ner on 7/20/17.
 */
class RowThreeViewHolder(override val rootView: View) : RowViewHolder<String> {
    private val text: TextView = rootView.findViewById<TextView>(R.id.textView)

    override fun bind(item: String) {
        text.text = item
    }
}