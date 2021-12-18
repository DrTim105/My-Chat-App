package com.salihutimothy.mychatapp.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.salihutimothy.mychatapp.R
import com.salihutimothy.mychatapp.models.User
import com.salihutimothy.mychatapp.views.ChatFromItem
import com.salihutimothy.mychatapp.views.ChatToItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class ChatLogActivity : AppCompatActivity() {
    companion object {
        val TAG = "ChatLog"
    }

    val adapter = GroupAdapter<ViewHolder>()

    var toUser: User? = null

    private lateinit var chatLog : RecyclerView
    private lateinit var sendMessage : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        chatLog = findViewById(R.id.recyclerview_chat_log)

        val adapter = GroupAdapter<ViewHolder>()

        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatFromItem())

        chatLog.adapter = adapter

        toUser = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)

        supportActionBar?.title = toUser?.username

//    setupDummyData()
//        listenForMessages()

//        send_button_chat_log.setOnClickListener {
//            Log.d(TAG, "Attempt to send message....")
//            performSendMessage()
//        }
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

//    private fun performSendMessage() {
//        // how do we actually send a message to firebase...
//        val text = edittext_chat_log.text.toString()
//
//        val fromId = FirebaseAuth.getInstance().uid
//        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
//        val toId = user.uid
//
//        if (fromId == null) return
//
//        val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
//
//        val chatMessage = ChatMessage(reference.key!!, text, fromId, toId, System.currentTimeMillis() / 1000)
//        reference.setValue(chatMessage)
//            .addOnSuccessListener {
//                Log.d(TAG, "Saved our chat message: ${reference.key}")
//            }
//    }
}
