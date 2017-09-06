package com.dtp.navigator.tabs

import android.view.View
import android.widget.TextView
import com.dtp.navigator.R
import com.dtp.renav.interfaces.RowViewHolder

/**
 * Created by ner on 7/20/17.
 */
class RowTwoViewHolder(override val rootView: View) : RowViewHolder<Long> {

    private val text: TextView = rootView.findViewById(R.id.textView)

    override fun bind(item: Long) {
        text.text = item.toString()
    }
}