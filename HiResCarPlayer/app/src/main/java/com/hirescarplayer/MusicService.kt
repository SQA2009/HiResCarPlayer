package com.example.hirescarplayer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserServiceCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.hirescarplayer.models.MusicTrack

class MusicService : MediaBrowserServiceCompat() {
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var musicPlayer: MusicPlayer
    private var trackList: List<MusicTrack> = listOf()
    private var currentTrackIndex: Int = 0

    private val prefsChangedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            musicPlayer.reloadProcessors(applicationContext)
        }
    }

    override fun onCreate() {
        super.onCreate()
        // ... your other setup code ...

        musicPlayer = MusicPlayer(this)

        // Register to listen for settings changes
        LocalBroadcastManager.getInstance(this).registerReceiver(
            prefsChangedReceiver,
            IntentFilter("com.example.hirescarplayer.PREFS_CHANGED")
        )
    }

    // ... rest of your MusicService code ...

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(prefsChangedReceiver)
        musicPlayer.release()
        mediaSession.release()
        super.onDestroy()
    }
}