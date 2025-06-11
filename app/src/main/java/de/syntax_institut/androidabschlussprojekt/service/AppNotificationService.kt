package de.syntax_institut.androidabschlussprojekt.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import de.syntax_institut.androidabschlussprojekt.MainActivity

class AppNotificationService(
    private val context: Context
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // versuche den channel zu laden
            val existingChannel =
                notificationManager.getNotificationChannel("AppNotificationChannel")

            // falls der channel noch nicht existiert wird er erstellt
            if (existingChannel == null) {
                val channel = NotificationChannel(
                    "AppNotificationChannel",
                    "DreamZzz",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Allgemeine Benachrichtigungen"
                }
                notificationManager.createNotificationChannel(channel)

            }
        }
    }

    fun showNotification(message: String, title: String) {

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // damit die activity nicht mehrmals läuft

        // erlaubt anderen apps einen intent in unserem namen auszuführen
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE) // intent wird als unveränderlich markiert, damit andere apps den intent nicht verändern könenn wenn sie ihn in unserem namen ausführen


        val notification = NotificationCompat.Builder(context, "AppNotificationChannel")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent) // wenn die notification angeklickt wird, wird der pendingIntent ausgeführt
            .setAutoCancel(true) // wenn der intent ausgeführt wird, wird die notification geschlossen
            .build()

        notificationManager.notify(1, notification)
    }

    fun areNotificationsEnabled(): Boolean {
        return notificationManager.areNotificationsEnabled()
    }
}
