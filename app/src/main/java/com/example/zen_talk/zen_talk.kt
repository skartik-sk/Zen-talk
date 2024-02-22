package com.example.zen_talk
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class ZenTalk : Application() {

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create a notification channel
            val channelId = "com.example.zen_talk"
            val channelName = "Zen_Talk"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance)

            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
