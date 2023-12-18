package com.example.kotlinfinal.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.kotlinfinal.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseInstanceIDService : FirebaseMessagingService() {


    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification.let {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(it?.channelId, "All", NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(channel)
            }

            val notificationBuilder =
                it?.channelId?.let { it1 ->
                    NotificationCompat.Builder(this, it1)
                        .setContentTitle(it.title)
                        .setContentText(it.body)
                        .setSmallIcon(R.drawable.shopping)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                }

            notificationManager.notify(1, notificationBuilder?.build())
        }
    }
}