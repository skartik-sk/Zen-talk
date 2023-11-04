package com.example.zen_talk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zen_talk.databinding.ActivityPincreatBinding

class pincreat : AppCompatActivity() {
    private lateinit var binding: ActivityPincreatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPincreatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            intent.putExtra("pin" , binding.e1.text.toString())
            startActivity(intent)
        }
    }
}