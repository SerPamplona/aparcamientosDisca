package com.sergiop.aparcamientos.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import com.sergiop.aparcamientos.R

class AppNotificacionManager constructor (val appContext : Context, val notManager : NotificationManager) {

    fun showLocalNotification (notification : NotificationData) {

        val notificationChannel = NotificationChannel(notification.channelId, notification.channelId, NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.GREEN
        notificationChannel.enableVibration(false)
        notManager.createNotificationChannel(notificationChannel)

        val builder = Notification  .Builder(appContext, notification.channelId)
                                    .setContentTitle(notification.title)
                                    .setContentText(notification.description)
                                    .setSmallIcon(R.drawable.ic_launcher_background)

        notManager.notify(0, builder.build())
    }
}