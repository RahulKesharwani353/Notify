package com.example.notificationapp

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Html
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager


class MainActivity2 : AppCompatActivity() {
    var tab: TableLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        tab = findViewById<View>(R.id.tab) as TableLayout
        val n = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (n.isNotificationPolicyAccessGranted) {
        } else {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, IntentFilter("Msg"))
    }

    private val onNotice: BroadcastReceiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun onReceive(context: Context?, intent: Intent) {
            val packageName = intent.getStringExtra("package")
            val titleData = intent.getStringExtra("title")
            val textData = intent.getStringExtra("text")
            val tr = TableRow(applicationContext)
            tr.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            val textview = TextView(applicationContext)
            textview.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1.0f
            )
            textview.textSize = 20f
            textview.setTextColor(Color.parseColor("#0B0719"))
            textview.text = Html.fromHtml("$packageName<br><b>$titleData : </b>$textData")
            tr.addView(textview)
            tab!!.addView(tr)

            var notificationHelper = NotificationHelper(this@MainActivity2)
            notificationHelper.createNotificationChannel()
            val title = "!st Notification"
            val message = "This is the notification message."
            notificationHelper.sendNotification(title, message)
        }
    }

}