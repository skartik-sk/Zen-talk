package com.example.zen_talk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.zen_talk.databinding.ActivityOtpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class OTP : AppCompatActivity() {
    private lateinit var binding:ActivityOtpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var varificatoinid:String
    private lateinit var dialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Please wait...")
        builder.setTitle("Loading")
        builder. setCancelable (false)
        dialog = builder. create()
        dialog.show()
        val kuch = intent.getStringExtra("number")

        val phno = "+91"+kuch
        binding.num.text = phno;
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber (phno)
            .setTimeout(  60L, TimeUnit.SECONDS).setActivity(this)
            .setCallbacks(object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted (p0: PhoneAuthCredential) {
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    dialog.dismiss()
                    Toast.makeText(  this@OTP,
                    "Please try again!! ${p0}",Toast.LENGTH_SHORT). show()}


                    override fun onCodeSent (po: String, p1: PhoneAuthProvider.ForceResendingToken) {
                        super.onCodeSent(po, p1)

                        dialog.dismiss()
                        varificatoinid = po


                    }

                    }).build()





        PhoneAuthProvider.verifyPhoneNumber(options)

        binding.button.setOnClickListener {

            if(binding.phnumber.text.isEmpty()){

                Toast.makeText(this, "plz enter otp", Toast.LENGTH_SHORT).show()

            }else{dialog.show()
                val  credential = PhoneAuthProvider.getCredential(varificatoinid, binding.phnumber.text!!.toString())


                auth.signInWithCredential(credential).addOnCompleteListener{
                    if(it.isSuccessful){
                        dialog.dismiss()
startActivity(Intent(this,Profile::class.java))
                        finish()
                    }else
                    {
                        Toast.makeText(this, "error ${it.exception}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}