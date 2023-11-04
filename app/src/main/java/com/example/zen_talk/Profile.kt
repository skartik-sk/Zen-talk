package com.example.zen_talk

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.zen_talk.databinding.ActivityProfileBinding
import com.example.zen_talk.model.usermodel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.Date

class Profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var
            auth: FirebaseAuth

    private
    lateinit
    var
            database: FirebaseDatabase

    private
    lateinit
    var
            storage: FirebaseStorage

    private lateinit
    var
            selectedImq: Uri
    private lateinit var
            dialoq: AlertDialog.Builder

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.button.setOnClickListener {
            if (binding.e1.text!!.isEmpty() || binding.e2.text!!.isEmpty() || binding.e3.text!!.isEmpty()) {
                Toast.makeText(this, "Enter all feilds", Toast.LENGTH_LONG).show()
            } else if (selectedImq == null) {
                Toast.makeText(this, "Please seleact your image", Toast.LENGTH_LONG).show()

            } else {
                uploaddata()
            }

        }



        dialoq = AlertDialog.Builder(this)
            .setMessage("Updating Profile..")
            .setCancelable(false)

        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()

        binding.imageView.setOnClickListener {
            val
                    intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)

        }


    }

    private fun uploaddata() {
        val reference = storage.reference.child("Profile").child(Date().time.toString())
        reference.putFile(selectedImq).addOnCompleteListener {
            if (it.isSuccessful) {
                reference.downloadUrl.addOnSuccessListener { task ->
                    uploadinfo(task.toString())
                }
            }
        }
    }

    private fun uploadinfo(ingurl: String) {
val user = usermodel(auth.uid.toString(), binding.e1.text.toString(),auth.currentUser!!.email.toString(), binding.e3.text.toString(), binding.e2.text.toString(),auth.currentUser!!.phoneNumber.toString(),ingurl,25)


        database. reference. child ( "users")
        .child(auth.uid.toString()).setValue(user).addOnSuccessListener {

                Toast.makeText(this, "chura liya mene data", Toast.LENGTH_LONG).show()

                val intent = Intent(this, home::class.java)
                startActivity(intent)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data
            !=
            null
        ) {


            if (data.data != null) {
                selectedImq = data.data!!;
                binding.imageView.setImageURI(selectedImq)
            }
        }
    }


}