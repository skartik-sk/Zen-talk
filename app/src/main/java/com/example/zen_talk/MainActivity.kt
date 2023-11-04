package com.example.zen_talk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.zen_talk.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        Thread.sleep(1000)
        installSplashScreen()
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.toSignup.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {

            val email = binding.e2.text.toString()
            val pass = binding.e3.text.toString()
            if(email.isNotEmpty() && pass.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if(it.isSuccessful){
                        val intent = Intent(this,auth::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(this,it.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }

            }
            else
            {
                Toast.makeText(this, "Fields cannot be blank" , Toast.LENGTH_LONG).show()
            }
        }
    }
}