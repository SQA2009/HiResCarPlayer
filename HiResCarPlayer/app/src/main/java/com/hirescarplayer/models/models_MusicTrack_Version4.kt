package com.example.hirescarplayer.models

import android.graphics.Bitmap
import android.net.Uri

data class MusicTrack(
    val title: String,
    val artist: String,
    val albumArt: Bitmap?,
    val fileUri: Uri
)