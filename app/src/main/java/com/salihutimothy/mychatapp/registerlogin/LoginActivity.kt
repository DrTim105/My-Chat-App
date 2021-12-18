package com.salihutimothy.mychatapp.registerlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.salihutimothy.mychatapp.messages.LatestMessagesActivity
import com.salihutimothy.mychatapp.R

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var backRegister: TextView
    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.login_button_login)
        backRegister = findViewById(R.id.back_to_register_textview)

        loginButton.setOnClickListener {
            performLogin()
        }

        backRegister.setOnClickListener{
            finish()
        }
    }

    private fun performLogin() {
        loginEmail = findViewById(R.id.email_edittext_login)
        loginPassword = findViewById(R.id.password_edittext_login)

        val email = loginEmail.text.toString()
        val password = loginPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill out email/password.", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                Log.d("Login", "Successfully logged in: ${it.result.user?.uid}")
                val intent = Intent(this, LatestMessagesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to log in: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}