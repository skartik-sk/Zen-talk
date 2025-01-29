package com.example.zen_talk.finalNotificatino

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class n1 : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            // Handle the data message here.
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            // Handle the notification message here.
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Update your app server with the new token.
    }
}