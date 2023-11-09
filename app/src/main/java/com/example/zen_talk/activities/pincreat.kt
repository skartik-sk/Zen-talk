package com.example.zen_talk.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.zen_talk.databinding.ActivityPincreatBinding

class pincreat : AppCompatActivity() {
    private lateinit var binding: ActivityPincreatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPincreatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            if (binding.e1.text!!.toString().length != 6)
            {
                Toast.makeText(this, "Please Enter 6 digit pin", Toast.LENGTH_SHORT).show()
            }
            else
            {
if(binding.e1.text!!.toString() == binding.e2.text!!.toString())
{


            val sharedPreferences = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("user_pin",binding.e1.text!!.toString() ) // Replace with the user's actual PIN
            editor.apply()
            val intent = Intent(this, Profile::class.java)
//            intent.putExtra("pin" , binding.e1.text.toString())
            startActivity(intent) }}
        }
    }
}