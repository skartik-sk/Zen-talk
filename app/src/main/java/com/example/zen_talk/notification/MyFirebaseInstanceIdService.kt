package com.example.zen_talk.notification

import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseInstanceIdService:FirebaseMessagingService() {

    override fun onNewToken(p0:String) {
        super.onNewToken(p0)
        val firebaseuser = FirebaseAuth.getInstance().currentUser
//        val refreshToken2= FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener {
//            var firebaseToken = it.result!!.token
//
//
//        }

        val refreshToken = FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(it.isSuccessful){
                if(firebaseuser!= null){
                    Toast.makeText(this@MyFirebaseInstanceIdService,"khya ho rha he",Toast.LENGTH_SHORT).show()
                    val nayatoken= it.result
                    updateToken (nayatoken)
                    Log.i("Mytoken" ,nayatoken)
                }
            }
        }

    }

    private fun updateToken(refreshToken: String?) {

        val
                firebaseUser = FirebaseAuth.getInstance().currentUser
        val ref = FirebaseDatabase.getInstance().reference.child(  "Tokens")
val token = Token(refreshToken!!)
        ref.child(firebaseUser!!.uid).setValue(token)
    }
}