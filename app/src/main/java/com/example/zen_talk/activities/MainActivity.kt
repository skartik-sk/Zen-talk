package com.example.zen_talk.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.zen_talk.R
import com.example.zen_talk.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val RC_SIGN_IN = 123 // Request code for Google Sign-In

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        val isFirstTime = sharedPreferences.getBoolean("is_first_time", true)

        if (isFirstTime) {
             val editor = sharedPreferences.edit()
            editor.putBoolean("is_first_time", false)
            editor.apply()
            setupProperLogin()
        } else {
            setupPINLogin()
        }
    }
    private fun setupProperLogin() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        // Set a click listener for your Google Sign-In button

        binding.imageButton2.setOnClickListener {
            startGoogleSignIn()
        }
        // Your existing code for email/password sign-in
        binding.button.setOnClickListener {
            val email = binding.e2.text.toString()
            val pass = binding.e3.text.toString()
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

        binding.toSignup.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
            finish()
        }
        binding.topin.setOnClickListener {
            val intent = Intent(this, enterpin::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun setupPINLogin() {
        // Start the PIN login activity (PIN login logic goes here)
        val intent = Intent(this, enterpin::class.java)
        startActivity(intent)
        finish()
    }

        // Set a click listener for your Google Sign-In button



        // Your existing code for email/password sign-in



    private fun startGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account.idToken

                if (idToken != null) {
                    val credential = GoogleAuthProvider.getCredential(idToken, null)
                    firebaseAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val user = firebaseAuth.currentUser
                                val intent = Intent(this, home::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            } catch (e: ApiException) {
                Toast.makeText(this, "Google Sign-In failed: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
