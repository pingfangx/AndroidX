package com.pingfangx.demo.androidx.activity.android.app.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

/**
 * 通知
 *
 * @author pingfangx
 * @date 2019/10/29
 */
class NotificationDemo {
    companion object {
        fun createChannel(context: Context, channelId: String, channelName: String = channelId, channelDesc: String = ""): String {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
                channel.description = channelDesc

                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
            return channelId
        }
    }
}