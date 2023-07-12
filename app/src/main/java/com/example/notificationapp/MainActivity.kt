package com.example.notificationapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.buttonNotification)
            .setOnClickListener {
                Toast.makeText(this, "Alarm set", Toast.LENGTH_SHORT).show()
                // Example usage: Sending a notification
                var notificationHelper = NotificationHelper(this)
                notificationHelper.createNotificationChannel()
                val title = "1st Notification"
                val message = "This is the notification message."
                notificationHelper.sendNotification(title, message)
                myAlarm()
            }

        findViewById<Button>(R.id.page2)
            .setOnClickListener {
                var intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
            }
    }

    private fun myAlarm() {
        val intent = Intent(applicationContext, NotificationReceiver::class.java)
        var pendingIntent: PendingIntent? = null
        pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getBroadcast(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            AlarmManager.INTERVAL_HALF_HOUR,
            pendingIntent
        )
    }
}