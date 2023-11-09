package com.example.zen_talk.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.zen_talk.R
import com.example.zen_talk.databinding.ActivityProfileBinding
import com.example.zen_talk.model.usermodel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.Date

class Profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private  var selectedImg: Uri =Uri.parse(" https://firebasestorage.googleapis.com/v0/b/zentalk-f2c93.appspot.com/o/com.example.zen_talk.activities.Profile%2F1699330045375?alt=media&token=89be677f-c598-4f41-9bea-fe67dc60ffad")
    private lateinit var dialog: AlertDialog


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Updating com.example.zen_talk.activities.Profile..")
        builder.setTitle("Loading")
        builder. setCancelable (false)
        dialog = builder.create()
        // Load the user's profile information from Firebase and populate the UI
        loadUserProfile()

        binding.button.setOnClickListener {
            if (binding.e1.text!!.isEmpty() || binding.e2.text!!.isEmpty() || binding.e3.text!!.isEmpty()) {
                Toast.makeText(this, "Enter all fields", Toast.LENGTH_LONG).show()
            } else {
                dialog.show()
                uploadData()
            }
        }

        binding.imageView.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
    }

    private fun loadUserProfile() {
        // Fetch user profile data from Firebase and populate UI fields
        val userRef = database.reference.child("users").child(auth.uid.toString())
        userRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val user = snapshot.getValue(usermodel::class.java)
                if (user != null) {
                    binding.e1.setText(user.name)
                    binding.e2.setText(user.about)
                    binding.e3.setText(user.username)
                    Glide.with(this).load(user.imageUrl).into(binding.imageView)
                    // Load the profile image using user.imageUrl
                    // You may use an image loading library like Glide or Picasso.
                    // Example: Glide.with(this).load(user.imageUrl).into(binding.imageView)
                }
            }
        }
    }

    private fun uploadData() {
        val reference = storage.reference.child("com.example.zen_talk.activities.Profile").child(Date().time.toString())
        reference.putFile(selectedImg).addOnCompleteListener {
            if (it.isSuccessful) {
                reference.downloadUrl.addOnSuccessListener { task ->
                    uploadInfo(task.toString())
                    dialog.dismiss()
                }
            }
        }
    }

    private fun uploadInfo(imgUrl: String) {
        val sharedPreferences = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        val storedPin = sharedPreferences.getString("user_pin", "")
        val user = usermodel(
            auth.uid.toString(),
            binding.e1.text.toString(),
            auth.currentUser!!.email.toString(),
            binding.e3.text.toString(),
            binding.e2.text.toString(),
            auth.currentUser!!.phoneNumber.toString(),
            imgUrl,
            storedPin?.toInt()
        )

        database.reference.child("users").child(auth.uid.toString()).setValue(user)
            .addOnSuccessListener {
                dialog.dismiss()
                Toast.makeText(this, "Data updated successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, home::class.java)
                startActivity(intent)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (data.data != null) {
                selectedImg = data.data!!
                binding.imageView.setImageURI(selectedImg)
            }
        }
    }
}
