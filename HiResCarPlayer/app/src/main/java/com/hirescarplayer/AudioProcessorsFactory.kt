package com.example.hirescarplayer

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.android.exoplayer2.audio.AudioProcessor
import com.google.android.exoplayer2.audio.SilenceSkippingAudioProcessor

object AudioProcessorsFactory {
    fun getAudioProcessors(context: Context): Array<AudioProcessor> {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val processors = mutableListOf<AudioProcessor>()

        val silenceSkipEnabled = prefs.getBoolean("silence_skip_enabled", true)
        if (silenceSkipEnabled) {
            val silenceProcessor = SilenceSkippingAudioProcessor()
            val thresholdDb = prefs.getInt("silence_skip_threshold", -40)
            silenceProcessor.setEnabled(true)
            silenceProcessor.setSilenceThresholdDb(thresholdDb)
            processors.add(silenceProcessor)
        }
        return processors.toTypedArray()
    }
}