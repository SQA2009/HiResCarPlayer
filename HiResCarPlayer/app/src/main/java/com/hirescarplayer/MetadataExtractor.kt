package com.example.hirescarplayer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import com.example.hirescarplayer.models.MusicTrack

object MetadataExtractor {
    fun extractMetadata(context: Context, fileUri: Uri): MusicTrack {
        val retriever = MediaMetadataRetriever()
        return try {
            retriever.setDataSource(context, fileUri)
            val title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
                ?: fileUri.lastPathSegment?.substringAfterLast('/') ?: "Unknown Title"
            val artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
                ?: "Unknown Artist"
            val artData = retriever.embeddedPicture
            val albumArt: Bitmap? = artData?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
            MusicTrack(
                title = title,
                artist = artist,
                albumArt = albumArt,
                fileUri = fileUri
            )
        } catch (e: Exception) {
            Log.w("MetadataExtractor", "Metadata extraction failed for $fileUri: ${e.localizedMessage}", e)
            MusicTrack(
                title = fileUri.lastPathSegment?.substringAfterLast('/') ?: "Unknown Title",
                artist = "Unknown Artist",
                albumArt = null,
                fileUri = fileUri
            )
        } finally {
            try { retriever.release() } catch (_: Exception) {}
        }
    }
}