package com.dtp.navigator.tabs

import android.view.View
import android.widget.TextView
import com.dtp.navigator.R
import com.dtp.renav.RowViewHolder

/**
 * Created by ner on 7/20/17.
 */
class RowOneViewHolder(override val rootView: View) : RowViewHolder<Int> {

    private val text: TextView = rootView.findViewById(R.id.textView)

    override fun bind(item: Int) {
        text.text = item.toString()
    }
}