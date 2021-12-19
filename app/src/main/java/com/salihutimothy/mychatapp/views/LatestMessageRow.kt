package com.salihutimothy.mychatapp.views

import android.widget.TextView
import com.salihutimothy.mychatapp.R
import com.salihutimothy.mychatapp.models.ChatMessage
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class LatestMessageRow(val chatMessage: ChatMessage): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val latestMessage = viewHolder.itemView.findViewById<TextView>(R.id.message_textview_latest_message)

        latestMessage.text = chatMessage.text
    }

    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }
}