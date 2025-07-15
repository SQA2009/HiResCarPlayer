package com.example.hirescarplayer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hirescarplayer.models.MusicTrack

class MainActivity : AppCompatActivity() {
    private lateinit var selectFolderButton: Button
    private lateinit var settingsButton: Button
    private lateinit var tracksRecyclerView: RecyclerView
    private lateinit var tracksAdapter: TracksAdapter
    private val musicTracks = mutableListOf<MusicTrack>()

    private val folderPicker = registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri: Uri? ->
        uri?.let {
            try {
                contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                PreferenceManager.getDefaultSharedPreferences(this)
                    .edit().putString("music_folder_uri", it.toString()).apply()
                scanAndDisplayTracks(it)
            } catch (e: Exception) {
                Log.e("MainActivity", "Error accessing folder: ${e.message}", e)
                Toast.makeText(this, "Error accessing folder: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectFolderButton = findViewById(R.id.btn_select_folder)
        settingsButton = findViewById(R.id.btn_settings)
        tracksRecyclerView = findViewById(R.id.recycler_tracks)

        tracksAdapter = TracksAdapter(musicTracks)
        tracksRecyclerView.adapter = tracksAdapter
        tracksRecyclerView.layoutManager = LinearLayoutManager(this)

        selectFolderButton.setOnClickListener { folderPicker.launch(null) }
        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        PreferenceManager.getDefaultSharedPreferences(this)
            .getString("music_folder_uri", null)
            ?.let { scanAndDisplayTracks(Uri.parse(it)) }
    }

    private fun scanAndDisplayTracks(folderUri: Uri) {
        try {
            val tracks = FileScanner.scanFolder(this, folderUri)
            musicTracks.clear()
            musicTracks.addAll(tracks)
            tracksAdapter.notifyDataSetChanged()
            if (tracks.isEmpty()) {
                Toast.makeText(this, "No supported music files found.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error scanning folder: ${e.message}", e)
            Toast.makeText(this, "Error reading folder: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
        }
    }
}