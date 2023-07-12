package com.example.notificationapp
import android.content.Context
import android.content.Intent
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager


class NotificationService : NotificationListenerService() {
    var context: Context? = null
    var titleData: String? = ""
    var textData = ""
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val packageName = sbn.packageName
        val extras = sbn.notification.extras
        titleData = if (extras.getString("android.title") != null) {
            extras.getString("android.title")
        } else {
            ""
        }
        textData = if (extras.getCharSequence("android.text") != null) {
            extras.getCharSequence("android.text").toString()
        } else {
            ""
        }
        Log.d("Package", packageName)
        Log.d("Title", titleData!!)
        Log.d("Text", textData)
        if(!packageName.equals("com.example.notificationapp")){
//            val msgrcv = Intent("Msg")
//            msgrcv.putExtra("package", packageName)
//            msgrcv.putExtra("title", titleData)
//            msgrcv.putExtra("text", textData)
//            LocalBroadcastManager.getInstance(context!!).sendBroadcast(msgrcv)
        }

    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        Log.d("Msg", "Notification Removed")
    }
}