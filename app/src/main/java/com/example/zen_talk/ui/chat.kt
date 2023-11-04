package com.example.zen_talk.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zen_talk.R
import com.example.zen_talk.adapter.chatadapter
import com.example.zen_talk.databinding.FragmentChatBinding
import com.example.zen_talk.model.usermodel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class chat : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private var database :FirebaseDatabase?=null
    lateinit var
            userList: ArrayList<usermodel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding= FragmentChatBinding.inflate(layoutInflater)

        database =
            FirebaseDatabase.getInstance()



        userList = ArrayList()
        database!!.reference.child("users").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (snapshor1 in snapshot.children){
                    val user = snapshor1.getValue(usermodel::class.java)
                    if(user!!.vid!=FirebaseAuth.getInstance().uid){
                        userList.add(user)
                    }
                }
                binding.userlistRecycler.adapter= chatadapter(requireContext(), userList)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return (binding.root)
    }


}