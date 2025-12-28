package com.example.smartcampusnotify.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.smartcampusnotify.R
import kotlin.random.Random

object Notifier {

    private const val CHANNEL_GENERAL = "general_updates"
    private const val CHANNEL_EMERGENCY = "emergency_alerts"

    fun ensureChannels(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val general = NotificationChannel(
            CHANNEL_GENERAL,
            "Genel Güncellemeler",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Takip edilen bildirimlerin durum güncellemeleri"
        }

        val emergency = NotificationChannel(
            CHANNEL_EMERGENCY,
            "Acil Bildirimler",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Admin tarafından yayınlanan acil duyurular"
        }

        nm.createNotificationChannel(general)
        nm.createNotificationChannel(emergency)
    }

    fun showGeneral(context: Context, title: String, message: String) {
        show(context, CHANNEL_GENERAL, title, message)
    }

    fun showEmergency(context: Context, title: String, message: String) {
        show(context, CHANNEL_EMERGENCY, title, message)
    }

    private fun show(context: Context, channelId: String, title: String, message: String) {
        ensureChannels(context)

        // Android 13+ için izin yoksa sessizce çık (crash olmasın)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!granted) return
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setAutoCancel(true)
            .setPriority(
                if (channelId == CHANNEL_EMERGENCY) NotificationCompat.PRIORITY_HIGH
                else NotificationCompat.PRIORITY_DEFAULT
            )

        NotificationManagerCompat.from(context)
            .notify(Random.nextInt(100000, 999999), builder.build())
    }
}
