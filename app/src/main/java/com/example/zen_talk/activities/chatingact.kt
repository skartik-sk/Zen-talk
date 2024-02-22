package com.example.zen_talk.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.provider.FontRequest
import com.bumptech.glide.Glide
import com.example.zen_talk.R
import com.example.zen_talk.adapter.message_adapter
import com.example.zen_talk.databinding.ActivityChatingactBinding
import com.example.zen_talk.model.messageModel
import com.example.zen_talk.model.usermodel
import com.example.zen_talk.notification.APIService
import com.example.zen_talk.notification.Data
import com.example.zen_talk.notification.MyResponce
import com.example.zen_talk.notification.Sender
import com.example.zen_talk.notification.Token
import com.example.zen_talk.notification.client

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.vanniktech.emoji.EmojiPopup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date
import kotlin.math.log

class chatingact : AppCompatActivity() {
    private lateinit var binding: ActivityChatingactBinding
    private lateinit var storage: FirebaseStorage
    private lateinit var database: FirebaseDatabase
    private lateinit var serderUID: String
    private lateinit var reciverUID: String
    private lateinit var senderroom: String
    private lateinit var reciveroomr: String
    private var selectedImg: Uri = Uri.EMPTY
    private lateinit var dialog: AlertDialog

    private lateinit var list: ArrayList<messageModel>
    var apiService: APIService?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatingactBinding.inflate(layoutInflater)
        database = FirebaseDatabase.getInstance()
        setContentView(binding.root)
        serderUID = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        reciverUID = intent.getStringExtra("uid")!!
        senderroom = serderUID + reciverUID
        reciveroomr = reciverUID + serderUID
        storage = FirebaseStorage.getInstance()
        apiService = client.client.gerClient("https://fcm.googleapis.com/")!!.create(APIService::class.java)
        list = ArrayList()
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Updating com.example.zen_talk.activities.Profile..")
        builder.setTitle("Loading")
        builder.setCancelable(false)
        dialog = builder.create()

//        val CERTIFICATES = null
//        val fontRequest = FontRequest(
//            "com.example.fontprovider",
//            "com.example",
//            "emoji compat Font Query",
//            CERTIFICATES
//        )
//        val config = FontRequestEmojiCompatConfig(this, fontRequest)
//        EmojiCompat.init(config)


        val userRef = database.reference.child("users").child(reciverUID)

        userRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val user = snapshot.getValue(usermodel::class.java)
                if (user != null) {
                    binding.userName.text = user.name

                    Glide.with(this).load(user.imageUrl).into(binding.userImage)
                }
            }
        }


        val IMAGE_PICK_REQUEST = 123
        binding.uploadimg.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_REQUEST)
        }


        binding.imageView3.setOnClickListener {
//            if (binding.messagebox.text.isEmpty()) {
//                Toast.makeText(this, "Please enter your message", Toast.LENGTH_SHORT).show()
//            } else {
//                uploadData()
//            }

            uploadData()
        }
        database.reference.child("chats").child(senderroom).child("message")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for (snapshot1 in snapshot.children) {
                        val data = snapshot1.getValue(messageModel::class.java)
                        list.add(data!!)
                    }
                    binding.recyclerView.adapter = message_adapter(this@chatingact, list)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@chatingact,

                        "     Error :$error ", Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }

    private fun uploadData() {
        Log.d(TAG, selectedImg.toString())
        if (binding.messagebox.text.isBlank() && selectedImg == Uri.EMPTY) {
            // If both the message and image are empty, return without uploading anything
            return
        }
        if (selectedImg == Uri.EMPTY) {
            uploadInfo("")
        } else {
            dialog.show()

            val reference = storage.reference.child("com.example.zen_talk.activities.Messages")
                .child(Date().time.toString())
            selectedImg.let {
                reference.putFile(it).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "gaya", Toast.LENGTH_SHORT).show()
                        reference.downloadUrl.addOnSuccessListener { task ->
                            uploadInfo(task.toString())
                        }
                    }
                }
            }
        }
    }


    private fun uploadInfo(imgUrl: String?) {
        val message = messageModel(binding.messagebox.text.toString(), serderUID, Date().time, imgUrl)
        val readomkey = database.reference.push().key
        database.reference.child("chats").child(senderroom).child("message").child(readomkey!!)
            .setValue(message).addOnSuccessListener {
                database.reference.child("chats").child(reciveroomr).child("message")
                    .child(readomkey!!).setValue(message).addOnSuccessListener {
                        database.reference.child("users").child(serderUID).get()
                            .addOnSuccessListener {
                                val user = it.getValue(usermodel::class.java)

                                    FirebaseMessaging.getInstance().token.addOnCompleteListener {
                                        if (it.isSuccessful) {

                                            // Toast.makeText(this, "khya ho rha he", Toast.LENGTH_SHORT).show()
                                            val nayatoken = it.result

                                            if (binding.messagebox.text.toString() != "") {
                                                sendNotification(
                                                    reciverUID,
                                                    user!!.username,
                                                    binding.messagebox.text.toString(),message.timestamp.toString(),
                                                    nayatoken.toString()
                                                )
                                            } else {
                                                sendNotification(
                                                    reciverUID, user!!.username, "Sent you and image",message.timestamp.toString()
                                                    ,nayatoken.toString()
                                                )

                                            }
                                        }
                                    }



                            }



                        dialog.dismiss()
                        binding.messagebox.text = null
                        Toast.makeText(this, "message send ", Toast.LENGTH_SHORT).show()
                        val marker = getResources().getDrawable(R.drawable.baseline_attach_file_24);
                        binding.uploadimg.setImageDrawable(marker)
                        selectedImg = Uri.EMPTY

                    }
            }
    }
private fun sendNotification(reciverUID: String, username: String?, message: String , time:String, token:String) {
    // Add log to check the data you're sending
    Log.d("NotificationData", "Receiver UID: $reciverUID, Username: $username, Message: $message, Time: $time, Token: $token")

    val ref = FirebaseDatabase.getInstance().reference.child("Tokens").orderByKey().equalTo(reciverUID).addValueEventListener(object :ValueEventListener{
        override fun onDataChange(p0: DataSnapshot) {
            for (dataSnapshort in p0.children){
                val data = Data(serderUID, R.mipmap.ic_launcher, "$username:$message", "New Message", reciverUID )
                val sender = Sender(data!!,token!!)
                apiService!!.sendNotification(sender)
                    .enqueue(object :Callback<MyResponce>{
                        override fun onResponse(
                            call: Call<MyResponce>,
                            response: Response<MyResponce>
                        ) {
                            // Add log to check the response from FCM server
                            Log.d("FCMResponse", "Response Code: ${response.code()}, Response Body: ${response.body()}")

                            if (response.code()==200){
                                if (response.body()!!.success!==1)
                                {
                                    Toast.makeText(this@chatingact, "failed, nothing heppend", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<MyResponce>, t: Throwable) {
                            // Add log to check the error message if the call fails
                            Log.e("FCMError", "Failed to send notification: ${t.message}")
                        }
                    })
            }
        }

        override fun onCancelled(error: DatabaseError) {
            // Add log to check the error message if the database operation fails
            Log.e("DatabaseError", "Failed to retrieve data: ${error.message}")
        }
    })
}
//    private fun sendNotification(reciverUID: String, username: String?, message: String , time:String, token:String) {
//val ref = FirebaseDatabase.getInstance().reference.child("Tokens").orderByKey().equalTo(reciverUID).addValueEventListener(object :ValueEventListener{
//    override fun onDataChange(p0: DataSnapshot) {
//        for (dataSnapshort in p0.children){
//           // val token: Token? = dataSnapshort.getValue(Token::class.java)
//            val data = Data(serderUID, R.mipmap.ic_launcher, "$username:$message", "New Message", reciverUID )
//            val sender = Sender(data!!,token!!)
//            apiService!!.sendNotification(sender)
//                .enqueue(object :Callback<MyResponce>{
//                    override fun onResponse(
//                        call: Call<MyResponce>,
//                        response: Response<MyResponce>
//                    ) {
//                        if (response.code()==200){
//                            Log.w("NOT", response.body()?.success.toString(), )
//
//                            if (response.body()!!.success!==1)
//                            {
//                                Log.w("NOT", response.body().toString(), )
//                                Toast.makeText(this@chatingact, "failed, nothing heppend", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    }
//
//                    override fun onFailure(call: Call<MyResponce>, t: Throwable) {
//                        TODO("Not yet implemented")
//                        Toast.makeText(this@chatingact, "Failed to send notification: ${t.message}", Toast.LENGTH_SHORT).show()
//
//                    }
//                })
//        }
//    }
//
//    override fun onCancelled(error: DatabaseError) {
//        TODO("Not yet implemented")
//    }
//})
//
//
//    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (data.data != null) {
                selectedImg = data.data!!
                binding.uploadimg.setImageURI(selectedImg)
            }
        }
    }
}


