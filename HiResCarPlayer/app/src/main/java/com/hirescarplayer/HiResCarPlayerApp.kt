package com.example.hirescarplayer

import android.app.Application
import android.util.Log

class HiResCarPlayerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationUtil.createPlaybackNotificationChannel(this)

        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("HiResCarPlayerApp", "CRASH in thread ${thread.name}: ${throwable.localizedMessage}", throwable)
            // TODO: Optionally report to Crashlytics or another crash reporter
        }
    }
}