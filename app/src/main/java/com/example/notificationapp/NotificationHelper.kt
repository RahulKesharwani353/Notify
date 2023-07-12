package com.example.notificationapp

import android.Manifest
import android.app.Activity
import android.app.Notification.DEFAULT_SOUND
import android.app.Notification.DEFAULT_VIBRATE
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.service.notification.StatusBarNotification
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlin.random.Random


class NotificationHelper(private val context: Context) {

    private val channelId = "my_channel_id1"
    private val channelName = "My Channel"
    private val notificationId = Random.nextInt(1, 100)
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "My Channel Description"
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun sendNotification(title: String, message: String) {
        val notificationLayout = RemoteViews(context.packageName, R.layout.custom_notification_layout)
        notificationLayout.setTextViewText(R.id.titleTextView, title)
        notificationLayout.setTextViewText(R.id.messageTextView, message)

        val fullScreenIntent = Intent(context, MainActivity::class.java)
        val fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
            fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)

        val builder = NotificationCompat.Builder(context, channelId)
            .setCustomContentView(notificationLayout)
            .setPriority(10)
            .setDefaults(DEFAULT_SOUND or DEFAULT_VIBRATE)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setGroup("GROUP_KEY_WORK_EMAIL")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(false)
            .setFullScreenIntent(fullScreenPendingIntent, true)


        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Check if notification permission is granted
                Toast.makeText(context, "Notification Permission missing", Toast.LENGTH_SHORT).show()
                return
            }
            notify(notificationId, builder.build())
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun updateNotification(title: String, message: String){
        val notificationLayout = RemoteViews(context.packageName, R.layout.custom_notification_layout)
        notificationLayout.setTextViewText(R.id.titleTextView, "$title $notificationId")
        notificationLayout.setTextViewText(R.id.messageTextView, message)

        val builder = NotificationCompat.Builder(context, channelId)
            .setCustomContentView(notificationLayout)
            .setPriority(10)
            .setDefaults(DEFAULT_SOUND or DEFAULT_VIBRATE)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setGroup("GROUP_KEY_WORK_EMAIL")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(false)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Check if notification permission is granted
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
                Toast.makeText(context, "Permission missing", Toast.LENGTH_SHORT).show()
                return
            }
            val notifications: Array<StatusBarNotification> =
                notificationManager.activeNotifications
            for (notification in notifications) {
                Log.d("Notifications ", "App: $notification")
                notify(notification.id, builder.build())
            }
        }

    }
}