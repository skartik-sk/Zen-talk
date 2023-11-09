package com.example.zen_talk.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.zen_talk.R
import com.example.zen_talk.databinding.ActivityEnterpinBinding
import com.google.firebase.auth.FirebaseAuth

class enterpin : AppCompatActivity() {
    private lateinit var binding: ActivityEnterpinBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEnterpinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        val sharedPreferences = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        binding.button.setOnClickListener {
            val enteredPin = binding.phnumber.text.toString()
            val storedPin = sharedPreferences.getString("user_pin", "")
            if (enteredPin == storedPin) {
            val email = sharedPreferences.getString("user_email", "")
            val pass =sharedPreferences.getString("user_password", "")
            if (pass != null) {
                if (email != null) {
                    if (email.isNotEmpty() && pass.isNotEmpty()) {
                        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                            if (it.isSuccessful) {
                                val intent = Intent(this, home::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Fields cannot be blank", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }}
        binding.textView4.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_first_time", true)
            editor.apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}