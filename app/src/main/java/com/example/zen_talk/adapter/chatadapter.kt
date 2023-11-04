package com.example.zen_talk.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.zen_talk.R
import com.example.zen_talk.chatingact
import com.example.zen_talk.databinding.ChatUserItemBinding
import com.example.zen_talk.model.usermodel

class chatadapter (var context: Context, var list: ArrayList<usermodel>): RecyclerView.Adapter<chatadapter.ChatViewHolder>() {
    inner class ChatViewHolder(view: View):RecyclerView.ViewHolder (view){
        val binding:ChatUserItemBinding = ChatUserItemBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_user_item, parent,  false))
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
       var user = list[position]
        Glide.with(context).load(user.imageUrl).into(holder.binding.userImage)
        holder.binding.userName.text= user.name
        holder.itemView.setOnClickListener{
            val intent = Intent(context,chatingact::class.java)
            intent.putExtra("uid", user.vid)
            context.startActivity(intent)
        }
    }
}
