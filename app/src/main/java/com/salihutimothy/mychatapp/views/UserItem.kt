package com.salihutimothy.mychatapp.views

import android.widget.TextView
import com.salihutimothy.mychatapp.R
import com.salihutimothy.mychatapp.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import de.hdodenhof.circleimageview.CircleImageView

class UserItem(val user: User): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

       val userName = viewHolder.itemView.findViewById<TextView>(R.id.username_textview_new_message)
        val userImage = viewHolder.itemView.findViewById<CircleImageView>(R.id.imageview_new_message)
        userName.text = user.username


        Picasso.get().load(user.profileImageUrl).into(userImage)
    }

    override fun getLayout(): Int {
        return R.layout.user_row
    }
}