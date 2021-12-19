package com.salihutimothy.mychatapp.views

import android.widget.ImageView
import android.widget.TextView
import com.salihutimothy.mychatapp.R
import com.salihutimothy.mychatapp.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class ChatFromItem(val text: String, val user: User): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

        val fromRow = viewHolder.itemView.findViewById<TextView>(R.id.textview_from_row)
//        val targetImageView = viewHolder.itemView.findViewById<ImageView>(R.id.imageview_chat_from_row)
        fromRow.text = text
//
//        val uri = user.profileImageUrl
//        Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem(val text: String, val user: User): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val toRow = viewHolder.itemView.findViewById<TextView>(R.id.textview_to_row)
//        val targetImageView = viewHolder.itemView.findViewById<ImageView>(R.id.imageview_chat_to_row)
        toRow.text = text
//
//        // load our user image into the star
//        val uri = user.profileImageUrl
//        Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}