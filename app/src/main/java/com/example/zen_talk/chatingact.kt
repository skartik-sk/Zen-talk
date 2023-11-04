package com.example.zen_talk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import com.example.zen_talk.adapter.message_adapter
import com.example.zen_talk.databinding.ActivityChatingactBinding
import com.example.zen_talk.model.messageModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Date

class chatingact : AppCompatActivity() {
    private lateinit var binding: ActivityChatingactBinding
    private lateinit var database:FirebaseDatabase
    private lateinit var serderUID: String
    private lateinit var reciverUID: String
    private lateinit var senderroom: String
    private lateinit var reciveroomr: String
    private lateinit var list : ArrayList<messageModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatingactBinding.inflate(layoutInflater)
        database =FirebaseDatabase.getInstance()
        setContentView(binding.root)
        serderUID = FirebaseAuth.getInstance().currentUser!!.uid.toString()
reciverUID = intent.getStringExtra("uid")!!
        senderroom = serderUID+reciverUID
        reciveroomr =reciverUID+ serderUID
list = ArrayList()
        binding.imageView3.setOnClickListener {
            if(binding.messagebox.text.isEmpty()){
                Toast.makeText (  this,  "Please enter your message", Toast.LENGTH_SHORT). show()
            }
            else
            {
                val message = messageModel(binding.messagebox.text.toString(),serderUID, Date().time)
                val readomkey = database.reference.push().key
                database.reference.child("chats").child(senderroom).child("message").child(readomkey!!).setValue(message).addOnSuccessListener {
                    database.reference.child("chats").child(reciveroomr).child("message").child(readomkey!!).setValue(message).addOnSuccessListener {
                        binding.messagebox.text = null
                        Toast.makeText(this, "message send ", Toast.LENGTH_SHORT).show()
                    }
                }


            }
        }

        database.reference.child("chats").child(senderroom).child("message")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for (snapshot1 in snapshot.children) {
                        val data = snapshot1.getValue(messageModel:: class. java)
                        list.add (data!!)}
                        binding.recyclerView.adapter = message_adapter(  this@chatingact, list)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast. makeText (  this@chatingact,

               "     Error :$error ",
                    Toast. LENGTH_SHORT). show()
                }

            })
    }
}


