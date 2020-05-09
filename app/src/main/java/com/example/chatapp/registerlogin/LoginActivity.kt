package com.example.chatapp.registerlogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.R
import com.example.chatapp.messages.LatestMessagesActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            val email = email_edittext_login.text.toString()
            val password = password_edittext_login.text.toString()

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter text in email/password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("MainActivity", "Login success ")
                        val intent = Intent(this, LatestMessagesActivity::class.java)
//                These flags force the back button to bring the user back to the home screen, and not the Register Activity
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("MainActivity", "Login failure", task.exception)
                        Toast.makeText(baseContext, "Login failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener(this) { task ->
                    Toast.makeText(this, "Failed. ${task.message}", Toast.LENGTH_SHORT).show()
                }
        }
        back_to_register_textview.setOnClickListener {
            finish()
        }
    }
}