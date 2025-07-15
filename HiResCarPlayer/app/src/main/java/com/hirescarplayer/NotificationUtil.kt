package com.example.hirescarplayer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationUtil {
    const val CHANNEL_ID = "hirescarplayer_playback"

    fun createPlaybackNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Playback",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = context.getSystemService(NotificationManager::class.java)
            try {
                manager.createNotificationChannel(channel)
            } catch (e: Exception) {
                // Defensive: don't crash if NotificationManager is unavailable
            }
        }
    }
}