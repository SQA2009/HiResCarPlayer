package com.example.hirescarplayer

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import com.example.hirescarplayer.models.MusicTrack

object FileScanner {
    fun scanFolder(context: Context, folderUri: Uri): List<MusicTrack> {
        val tracks = mutableListOf<MusicTrack>()
        try {
            val folder = DocumentFile.fromTreeUri(context, folderUri)
            val supportedExtensions = listOf("flac", "wav", "alac", "mp3")
            folder?.listFiles()?.forEach { file ->
                try {
                    if (file.isFile && supportedExtensions.any { file.name.orEmpty().endsWith(it, true) }) {
                        val track = MetadataExtractor.extractMetadata(context, file.uri)
                        tracks.add(track)
                    }
                } catch (trackEx: Exception) {
                    Log.w("FileScanner", "Failed to extract metadata for ${file.name}: ${trackEx.localizedMessage}", trackEx)
                }
            }
        } catch (e: Exception) {
            Log.e("FileScanner", "Error scanning folder: ${e.message}", e)
        }
        return tracks.sortedBy { it.title }
    }
}