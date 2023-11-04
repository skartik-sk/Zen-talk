package com.example.zen_talk.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.zen_talk.R
import com.example.zen_talk.databinding.ResitemBinding
import com.example.zen_talk.databinding.SenditemBinding
import com.example.zen_talk.model.messageModel
import com.google.firebase.auth.FirebaseAuth

class message_adapter(var context: Context, var list: ArrayList<messageModel>)
    :RecyclerView.Adapter<
        RecyclerView.ViewHolder>() {


    var item_send = 1;
    var item_recieve = 2;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return if (viewType == item_send)
           sendviewholder(LayoutInflater.from(context).inflate(R.layout.senditem, parent, false))
        else {
           recieveviewholder(LayoutInflater.from(context).inflate(R.layout.resitem, parent, false))

       }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(FirebaseAuth.getInstance().uid== list[position].serdrerId)item_send else item_recieve
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val message = list[position]
        if (holder.itemViewType == item_send)
        {
            val viewHolder = holder as sendviewholder
            viewHolder.binding.usermessage.text = message.message
        }
        else{
            val viewHolder = holder as recieveviewholder
            viewHolder.binding.usermessage.text = message.message
        }
    }

    inner  class sendviewholder(view: View):RecyclerView.ViewHolder(view){
        val binding = SenditemBinding.bind(view)
    }
    inner  class recieveviewholder(view: View):RecyclerView.ViewHolder(view){
        val binding = ResitemBinding.bind(view)
    }
}