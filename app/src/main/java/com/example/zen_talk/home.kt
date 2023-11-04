package com.example.zen_talk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.example.zen_talk.adapter.viewspager
import com.example.zen_talk.databinding.ActivityHomeBinding
import com.example.zen_talk.databinding.ActivityMainBinding
import com.example.zen_talk.ui.call
import com.example.zen_talk.ui.chat
import com.example.zen_talk.ui.group
import com.google.firebase.auth.FirebaseAuth

class home : AppCompatActivity() {
    private var binding : ActivityHomeBinding?= null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val fragmentarrlist = ArrayList<Fragment>()
        fragmentarrlist.add(chat())
        fragmentarrlist.add(group())
        fragmentarrlist.add(call())
        auth = FirebaseAuth.getInstance()
        if(auth.currentUser== null){
            startActivity(Intent(this,auth::class.java))
            finish()
        }
        val adtapter = viewspager(this, supportFragmentManager, fragmentarrlist)
        binding!!.viewPager.adapter=adtapter
        binding!!.tabs.setupWithViewPager(binding!!.viewPager)
        binding!!.toProfile.setOnClickListener {
            startActivity(Intent(this,Profile::class.java))
        }
    }

}