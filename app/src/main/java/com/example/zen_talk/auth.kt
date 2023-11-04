package com.example.zen_talk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.zen_talk.databinding.ActivityAuthBinding
import com.example.zen_talk.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class auth : AppCompatActivity() {
    private lateinit var binding:ActivityAuthBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.button.setOnClickListener {
            if(
                binding.phnumber.text!!.isEmpty()
            ){
                Toast.makeText(this,"Please enter your number",Toast.LENGTH_SHORT).show()
            }
            else{
                var intent = Intent(this, OTP::class.java)
                intent.putExtra("number", binding.phnumber.text!!.toString())
                startActivity(intent)
            }

        }
    }
}