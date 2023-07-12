package com.example.notificationapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

class NotificationReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context?, intent: Intent?) {
        var notificationHelper = NotificationHelper(context!!)
        notificationHelper.createNotificationChannel()
        val title = "Notification"
        val message = "Updated Notification."
        notificationHelper.sendNotification(title, message)
        notificationHelper. updateNotification("Notification","updated Notification")
    }
}