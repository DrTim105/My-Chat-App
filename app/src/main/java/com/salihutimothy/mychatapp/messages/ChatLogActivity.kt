package com.salihutimothy.mychatapp.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.salihutimothy.mychatapp.R
import com.salihutimothy.mychatapp.models.ChatMessage
import com.salihutimothy.mychatapp.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import de.hdodenhof.circleimageview.CircleImageView

class ChatLogActivity : AppCompatActivity() {
    companion object {
        val TAG = "ChatLog"
    }

    val adapter = GroupAdapter<ViewHolder>()

    var toUser: User? = null

    private lateinit var chatLog: RecyclerView
    private lateinit var sendMessage: Button
    private lateinit var chatText: EditText
    private lateinit var toUserName: TextView
    private lateinit var toUserImage: CircleImageView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        chatLog = findViewById(R.id.recyclerview_chat_log)
        sendMessage = findViewById(R.id.send_button_chat_log)
        toUserName = findViewById(R.id.to_user_name)
        toUserImage = findViewById(R.id.to_user_image)
        chatText = findViewById(R.id.edittext_chat_log)

        val adapter = GroupAdapter<ViewHolder>()

//        adapter.add(ChatFromItem())
//        adapter.add(ChatToItem())
//        adapter.add(ChatFromItem())
//        adapter.add(ChatFromItem())
//        adapter.add(ChatToItem())
//        adapter.add(ChatFromItem())
//        adapter.add(ChatToItem())
//        adapter.add(ChatFromItem())
//        adapter.add(ChatToItem())
//        adapter.add(ChatToItem())
//        adapter.add(ChatFromItem())
//        adapter.add(ChatFromItem())

        chatLog.adapter = adapter

        toUser = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)

        toUserName.text = toUser?.username
        Picasso.get().load(toUser?.profileImageUrl).into(toUserImage)

//        listenForMessages()


        sendMessage.setOnClickListener {
            Log.d(TAG, "Attempt to send message....")
            performSendMessage()
        }
    }

//    private fun listenForMessages() {
//        val ref = FirebaseDatabase.getInstance().getReference("/messages")
//
//        ref.addChildEventListener(object: ChildEventListener {
//
//            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
//                val chatMessage = p0.getValue(ChatMessage::class.java)
//
//                if (chatMessage != null) {
//                    Log.d(TAG, chatMessage.text)
//
//                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
//                        val currentUser = LatestMessagesActivity.currentUser ?: return
//                        adapter.add(ChatFromItem(chatMessage.text, currentUser))
//                    } else {
//                        adapter.add(ChatToItem(chatMessage.text, toUser!!))
//                    }
//                }
//
//            }
//
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//
//            }
//
//            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//
//            }
//
//            override fun onChildRemoved(p0: DataSnapshot) {
//
//            }
//
//        })
//
//    }

    private fun performSendMessage() {
        chatText = findViewById(R.id.edittext_chat_log)
        chatLog = findViewById(R.id.recyclerview_chat_log)
        val text = chatText.text.toString()

        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val toId = user!!.uid

        if (fromId == null) return

        val reference =
            FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()

        val toReference =
            FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()

        val chatMessage =
            ChatMessage(reference.key!!, text, fromId, toId, System.currentTimeMillis() / 1000)

        reference.setValue(chatMessage)
            .addOnCompleteListener {
                Log.d(TAG, "Saved our chat message: ${reference.key}")
                chatText.text.clear()
                chatLog.scrollToPosition(adapter.itemCount - 1)
            }
            .addOnFailureListener {
                Log.d(TAG, "failed to save message")
            }

        toReference.setValue(chatMessage)
    }

}
