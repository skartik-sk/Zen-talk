package com.example.zen_talk.notification
import android.Manifest
import android.app.NotificationChannel
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog.Builder
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.zen_talk.R
import com.example.zen_talk.activities.chatingact
import com.example.zen_talk.adapter.chatadapter
import com.google.firebase.auth.FirebaseAuth
import java.net.URI


import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("TAG", "Message data payload: ${remoteMessage.data}")

            // Handle the data message here.

            // For example, you can send a notification like this:
            remoteMessage.data["message"]?.let { sendNotification(it) }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d("TAG", "Message Notification Body: ${remoteMessage.notification!!.body}")
        }
    }



private fun sendNotification(messageBody: String) {
    val channelId = "com.example.zen_talk"
    val channelName = "Zen_Talk"
    val notificationId = 101 // This should be a unique int for each notification

    // Create a notification channel for Android O and above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, importance)
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // Build the notification
    val builder = NotificationCompat.Builder(this, channelId)
        .setSmallIcon(R.drawable.authentication) // Replace with your own icon
        .setContentTitle("New Message")
        .setContentText(messageBody)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    // Show the notification
    with(NotificationManagerCompat.from(this)) {
        if (ActivityCompat.checkSelfPermission(
                this@MyFirebaseMessagingService,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notify(notificationId, builder.build())
    }
}
}
//
//class MyFirebaseMessagingService : FirebaseMessagingService() {
//
//    // Handle incoming message and create a notification here
//    override fun onMessageReceived(mRemoteMessage: RemoteMessage) {
//        super.onMessageReceived(mRemoteMessage)
//
//        if (mRemoteMessage.data.isNotEmpty()) {
//            Log.d("testing", "Message data payload: ${mRemoteMessage.data}")
//        }
//            if (mRemoteMessage.notification != null) {
//            Log.d("faltu", "Message Notification Body: ${mRemoteMessage.notification!!.body}")
//        }
//        val sented = mRemoteMessage.data["sented"]
//        val user = mRemoteMessage.data["user"]
//        val sharedPref = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
//        val currentonlineuser = sharedPref.getString("currentUser", "none")
//        val firebaseUser = FirebaseAuth.getInstance().currentUser
//        if (firebaseUser != null && sented == firebaseUser.uid) {
//            if (currentonlineuser != user) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    sendoreoNotification(mRemoteMessage)
//                } else {
//                    sendNotification(mRemoteMessage)
//                }
//            }
//        }
//
////            val messageBody = remoteMessage.notification?.body
////            val messageTitle = remoteMessage.notification?.title
////
////            val builder = NotificationCompat.Builder(this, "channel_id")
////                .setSmallIcon(R.drawable.ic_notification)
////                .setContentTitle(messageTitle)
////                .setContentText(messageBody)
////                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
////
////            val notificationManager = NotificationManagerCompat.from(this)
////            notificationManager.notify(0, builder.build())
//
//    }
//
//    private fun sendNotification(mRemoteMessage: RemoteMessage) {
//        val user = mRemoteMessage.data["user"]
//        val icon = mRemoteMessage.data["icon"]
//        val title = mRemoteMessage.data["title"]
//        val body = mRemoteMessage.data["body"]
//        val notification = mRemoteMessage.notification
//        val j = user!!.replace("[//D]".toRegex(), "").toInt()
//        val intent = Intent(this, chatingact::class.java)
//        val bundle = Bundle()
//        bundle.putString("userId", user)
//        intent.putExtras(bundle)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(
//            this, j, intent,
//            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
//        )
//        val defaultsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this)
//            .setContentIntent(pendingIntent)
//            .setContentTitle(title)
//            .setContentText(body)
//            .setSmallIcon(icon!!.toInt())
//            .setSound(defaultsound)
//            .setAutoCancel(true)
//        val noti = getSystemService(Context.NOTIFICATION_SERVICE)
//                as NotificationManager
//
//        var i = 0
//        if (j > 0) {
//            i = j
//        }
//        noti.notify(i, builder.build())
//    }
//
//
//    private fun sendoreoNotification(mRemoteMessage: RemoteMessage) {
//        val user = mRemoteMessage.data["user"]
//        val icon = mRemoteMessage.data["icon"]
//        val title = mRemoteMessage.data["title"]
//        val body = mRemoteMessage.data["body"]
//        val notification = mRemoteMessage.notification
//        val j = user!!.replace("[//D]".toRegex(), "").toInt()
//        val intent = Intent(this, chatingact::class.java)
//        val bundle = Bundle()
//        bundle.putString("userId", user)
//        intent.putExtras(bundle)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(
//            this, j, intent,
//            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
//        )
//        val defaultsound: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val oreoNotification = OreoNotification(this)
//        val builder: Notification.Builder = oreoNotification.getoreoNotification(
//            title, body, pendingIntent, defaultsound, icon
//
//        )
//
//        var i = 0
//        if (j > 0) {
//            i = j
//        }
//        oreoNotification.getManager!!.notify(i, builder.build())
//    }
//}

