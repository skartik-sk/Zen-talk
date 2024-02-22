package com.example.zen_talk.notification

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.os.Build
import com.example.zen_talk.R

class OreoNotification(base: Context?) : ContextWrapper(base) {
    private var notificationManager: NotificationManager? = null

    companion object {
        private const val CHANNEL_ID = "com.example.zen_talk"
        private const val CHANNEL_NAME = "Zen-talk"
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID, CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )


        channel.enableLights(false)
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        getManager!!.createNotificationChannel(channel)
    }

    val getManager: NotificationManager?
        get() {
            if (notificationManager == null) {
                notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            }
            return notificationManager
        }

    @TargetApi(Build.VERSION_CODES.O)
    fun getoreoNotification(
        title: String?,
        body: String?,
        pendingIntent: PendingIntent?,
        sounduri: Uri?,
        icon: String?
    ): Notification.Builder {
        return Notification.Builder(applicationContext, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setContentText(body)
            .setChannelId(CHANNEL_ID)
            .setSmallIcon(icon!!.toInt())
            .setSound(sounduri)
            .setAutoCancel(true)
//
    }

}