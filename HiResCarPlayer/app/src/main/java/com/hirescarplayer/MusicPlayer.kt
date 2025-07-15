package com.example.hirescarplayer

import android.content.Context
import android.util.Log
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.audio.DefaultAudioSink
import com.google.android.exoplayer2.audio.AudioProcessor

class MusicPlayer(context: Context) {
    private var processors: Array<AudioProcessor> = AudioProcessorsFactory.getAudioProcessors(context)
    var exoPlayer: ExoPlayer = buildPlayer(context)

    private fun buildPlayer(context: Context): ExoPlayer {
        processors = AudioProcessorsFactory.getAudioProcessors(context)
        val player = ExoPlayer.Builder(context)
            .setAudioSink(
                DefaultAudioSink.Builder()
                    .setEnableFloatOutput(true)
                    .setAudioProcessors(processors)
                    .build()
            ).build()
        return player
    }

    fun reloadProcessors(context: Context) {
        try {
            exoPlayer.release()
        } catch (e: Exception) {
            Log.w("MusicPlayer", "Error releasing player: ${e.localizedMessage}", e)
        }
        exoPlayer = buildPlayer(context)
    }

    fun play(mediaItem: MediaItem) {
        try {
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.play()
        } catch (e: Exception) {
            Log.e("MusicPlayer", "Playback error: ${e.localizedMessage}", e)
        }
    }

    fun release() {
        try {
            exoPlayer.release()
        } catch (e: Exception) {
            Log.w("MusicPlayer", "Error releasing player: ${e.localizedMessage}", e)
        }
    }
}