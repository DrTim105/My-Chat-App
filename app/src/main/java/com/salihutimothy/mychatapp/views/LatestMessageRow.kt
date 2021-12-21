package com.salihutimothy.mychatapp.views

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.salihutimothy.mychatapp.R
import com.salihutimothy.mychatapp.models.ChatMessage
import com.salihutimothy.mychatapp.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class LatestMessageRow(private val chatMessage: ChatMessage) : Item<ViewHolder>() {
    var chatPartnerUser: User? = null

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val latestMessage =
            viewHolder.itemView.findViewById<TextView>(R.id.message_textview_latest_message)
        val chatName =
            viewHolder.itemView.findViewById<TextView>(R.id.username_textview_latest_message)
        val chatImage =
            viewHolder.itemView.findViewById<ImageView>(R.id.imageview_latest_message)

        latestMessage.text = chatMessage.text
        val chatPartnerId: String = if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
            chatMessage.toId
        } else {
            chatMessage.fromId
        }

        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                chatPartnerUser = p0.getValue(User::class.java)
                Log.d("LatestMEssageRow", "User name is ${chatPartnerUser?.username}")

                chatName.text = chatPartnerUser?.username

                Picasso.get().load(chatPartnerUser?.profileImageUrl).into(chatImage)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }
}