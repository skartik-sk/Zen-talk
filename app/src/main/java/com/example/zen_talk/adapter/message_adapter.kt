package com.example.zen_talk.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.zen_talk.R
import com.example.zen_talk.databinding.ResimageitemBinding
import com.example.zen_talk.databinding.ResitemBinding
import com.example.zen_talk.databinding.SendimageitemBinding
import com.example.zen_talk.databinding.SenditemBinding
import com.example.zen_talk.model.messageModel
import com.google.firebase.auth.FirebaseAuth
class message_adapter(var context: Context, var list: ArrayList<messageModel>)
    : Adapter<
        ViewHolder>() {


    private val item_send_text = 1
    private val item_send_image = 2
    private val item_recieve_text = 3
    private val item_recieve_image = 4


    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(R.id.usermessage)
//        val messageImage: ImageView = itemView.findViewById(R.id.messageImage)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when (viewType) {
            item_send_text -> {
                return sendviewholder(
                    LayoutInflater.from(context).inflate(R.layout.senditem, parent, false)
                )
            }
            item_send_image -> {
                return sendviewholderforimg(
                    LayoutInflater.from(context).inflate(R.layout.sendimageitem, parent, false)
                )
            }
            item_recieve_text -> {
                return recieveviewholder(
                    LayoutInflater.from(context).inflate(R.layout.resitem, parent, false)
                )
            }
            item_recieve_image -> {
                return recieveviewholderforimg(
                    LayoutInflater.from(context).inflate(R.layout.resimageitem, parent, false)
                )
            }
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        }
    }

    // Define a function to determine if the message is an image
    private fun messageTypeIsImage(message: messageModel): Boolean {
        return !message.imageUrl.isNullOrBlank()
    }



//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//       return if (viewType == item_send){
//           sendviewholder(LayoutInflater.from(context).inflate(R.layout.senditem, parent, false))
//       } else {
//           recieveviewholder(LayoutInflater.from(context).inflate(R.layout.resitem, parent, false))
//       }
////        return if (viewType == item_send)
////            MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.senditem, parent, false))
////        else {
////            MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.resitem, parent, false))
////       }
//
//    }

    override fun getItemCount(): Int {
        return list.size
    }
    override fun getItemViewType(position: Int): Int {
        val message = list[position]
        return when {
            // Check whether the message is text or image and return the appropriate view type
            messageTypeIsImage(message) -> {
                if (FirebaseAuth.getInstance().uid == message.serdrerId) {
                    item_send_image
                } else {
                    item_recieve_image
                }
            }
            else -> {
                if (FirebaseAuth.getInstance().uid == message.serdrerId) {
                    item_send_text
                } else {
                    item_recieve_text
                }
            }
        }
    }

//    override fun getItemViewType(position: Int): Int {
//        return if(FirebaseAuth.getInstance().uid== list[position].serdrerId)item_send else item_recieve
//    }
override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val message = list[position]

    when (holder.itemViewType) {
        item_send_text -> {
            if (holder is sendviewholder) {
                // This is a text message sent by the user
                if (message.message!!.isNotEmpty()) {
                    holder.binding.usermessage.text = message.message
                } else {
                    holder.binding.usermessage.text = "Empty text"
                }
            }
        }
        item_send_image -> {
            if (holder is sendviewholderforimg) {
                // This is an image message sent by the user
                Glide.with(holder.itemView.context).load(message.imageUrl).into(holder.binding.userimage)
            }
        }
        item_recieve_text -> {
            if (holder is recieveviewholder) {
                // This is a text message received by the user
                if (message.message!!.isNotEmpty()) {
                    holder.binding.usermessage.text = message.message
                } else {
                    holder.binding.usermessage.text = "Empty text"
                }
            }
        }
        item_recieve_image -> {
            if (holder is recieveviewholderforimg) {
                // This is an image message received by the user
                Glide.with(holder.itemView.context).load(message.imageUrl).into(holder.binding.userimage)
            }
        }
    }
}

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//       val message = list[position]
//        if (holder.itemViewType == item_send)
//        {
//            if (message.imageUrl==""){
//
//            }
//            else{
//                val viewHolder = holder as sendviewholderforimg
//                Glide.with(holder.itemView.context).load(message.imageUrl).into( viewHolder.binding.userimage)
//
//
//            }
//            if(message.message.toString()!= ""){
//
//
//            val viewHolder = holder as sendviewholder
//            viewHolder.binding.usermessage.text = message.message
//
//            }
//
//        }
//        else{
//            if (message.imageUrl==""){
//
//            }
//            else{
//                val viewHolder = holder as recieveviewholderforimg
//                Glide.with(holder.itemView.context).load(message.imageUrl).into( viewHolder.binding.userimage)
//
//
//            }
//            if(message.message.toString()!= ""){
//
//                val viewHolder = holder as recieveviewholder
//            viewHolder.binding.usermessage.text = message.message}
//        }
////        val message = list[position]
////        if (holder.itemViewType == item_send)
////        {
////            val viewHolder = holder as sendviewholder
////
////
////            if (message.imageUrl.isNotEmpty()) {
////                viewHolder.binding.messageImage.visibility = View.VISIBLE
////                Glide.with(context).load(message.imageUrl).into(viewHolder.binding.messageImage)
////                // Hide the text view
////                viewHolder.binding.usermessage.visibility = View.GONE
////            } else {
////                // Display the text message
////                viewHolder.binding.messageImage.visibility = View.GONE
////                viewHolder.binding.usermessage.visibility = View.VISIBLE
////                viewHolder.binding.usermessage.text = message.message
////            }
////        }
////        else{
////            val viewHolder = holder as recieveviewholder
////            if (message.imageUrl.isNotEmpty()) {
////                viewHolder.binding.messageImage.visibility = View.VISIBLE
////                Glide.with(context).load(message.imageUrl).into(viewHolder.binding.messageImage)
////                // Hide the text view
////                viewHolder.binding.usermessage.visibility = View.GONE
////            } else {
////                // Display the text message
////                viewHolder.binding.messageImage.visibility = View.GONE
////                viewHolder.binding.usermessage.visibility = View.VISIBLE
////                viewHolder.binding.usermessage.text = message.message
////            }
////        }
//
//
//
//    }

    inner  class sendviewholder(view: View): ViewHolder(view){
        val binding = SenditemBinding.bind(view)
    }
    inner  class recieveviewholder(view: View): ViewHolder(view){
        val binding = ResitemBinding.bind(view)
    }
    inner  class sendviewholderforimg(view: View): ViewHolder(view){
        val binding = SendimageitemBinding.bind(view)
    }
    inner  class recieveviewholderforimg(view: View): ViewHolder(view){
        val binding = ResimageitemBinding.bind(view)
    }
}