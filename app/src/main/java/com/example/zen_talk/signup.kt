package com.example.zen_talk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.zen_talk.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class signup : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.toLoign.setOnClickListener {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

        }
        binding.button.setOnClickListener {
            val name = binding.e1.text.toString()
            val email = binding.e2.text.toString()
            val pass = binding.e3.text.toString()
            val conpass = binding.e4.text.toString()
//            var kuch= username(name)
//            Toast.makeText(this, kuch.user, Toast.LENGTH_LONG).show()

            if (email.isNotEmpty() && pass.isNotEmpty() &&name.isNotEmpty()&&conpass.isNotEmpty()) {
                if (pass == conpass) {


                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {


                        val intent = Intent(this, auth::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }

            }
                else{
                Toast.makeText(this, "Confirm pass is not same", Toast.LENGTH_LONG).show()
            } } else {
                Toast.makeText(this, "Fields cannot be blank", Toast.LENGTH_LONG).show()
            }

        }
    }
}