package com.salihutimothy.mychatapp.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.salihutimothy.mychatapp.R
import com.salihutimothy.mychatapp.messages.LatestMessagesActivity.Companion.currentUser
import com.salihutimothy.mychatapp.models.ChatMessage
import com.salihutimothy.mychatapp.models.User
import com.salihutimothy.mychatapp.views.ChatFromItem
import com.salihutimothy.mychatapp.views.ChatToItem
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


        chatLog.adapter = adapter

        toUser = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)

        toUserName.text = toUser?.username
        Picasso.get().load(toUser?.profileImageUrl).into(toUserImage)

        listenForMessages()


        sendMessage.setOnClickListener {
            Log.d(TAG, "Attempt to send message....")
            performSendMessage()
        }
    }

    private fun listenForMessages() {
        val fromId = FirebaseAuth.getInstance().uid
        val toId = toUser?.uid
        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")

        ref.addChildEventListener(object: ChildEventListener {

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)

                if (chatMessage != null) {
                    Log.d(TAG, chatMessage.text)

                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        val currentUser = LatestMessagesActivity.currentUser ?: return
                        adapter.add(ChatToItem(chatMessage.text, currentUser))
                    } else {
                        adapter.add(ChatFromItem(chatMessage.text, toUser!!))
                    }
                }

            }

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })

    }

    private fun performSendMessage() {
        chatText = findViewById(R.id.edittext_chat_log)
        chatLog = findViewById(R.id.recyclerview_chat_log)
        val text = chatText.text.toString()

        if (text.isNotEmpty()) {
            val fromId = FirebaseAuth.getInstance().uid
            val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
            val toId = user!!.uid
            val toUsername = toUser?.username
            val fromUsername = currentUser?.username

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

            val latestMessageRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId/$toId")

            val latestFromChatMessage = LatestChatMessage(fromUsername!!, reference.key!!,text, fromId,
                toId, System.currentTimeMillis() / 1000, false)

            val latestToChatMessage = LatestChatMessage(toUsername!!, reference.key!!,text, fromId,
                toId, System.currentTimeMillis() / 1000, false)

            latestMessageRef.setValue(latestToChatMessage)

            val latestMessageToRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$toId/$fromId")

            latestMessageToRef.setValue(latestFromChatMessage)
        }

    }

    fun keyboardManagement(){
        // starts chat from bottom
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        chatLog = findViewById(R.id.recyclerview_chat_log)

        chatLog.layoutManager = layoutManager

        // pushes up recycler view when softkeyboard popups up
        chatLog.addOnLayoutChangeListener { view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (bottom < oldBottom) {
                chatLog.postDelayed(Runnable {
                    chatLog.scrollToPosition(
                        chatLog.adapter!!.itemCount -1)
                }, 100)
            }
        }
    }

    class LatestChatMessage(val toUsername:String, val id:String, val message:String, val fromId:String, val toID:String, val timestamp:Long, val messageSeen:Boolean){
        constructor(): this("","","","","",-1, false)
    }

}


