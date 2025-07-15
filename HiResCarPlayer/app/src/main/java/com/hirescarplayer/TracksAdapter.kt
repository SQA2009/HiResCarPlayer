package com.example.hirescarplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hirescarplayer.models.MusicTrack

class TracksAdapter(private val tracks: List<MusicTrack>) : RecyclerView.Adapter<TracksAdapter.TrackViewHolder>() {

    class TrackViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.track_title)
        val artist: TextView = view.findViewById(R.id.track_artist)
        val albumArt: ImageView = view.findViewById(R.id.track_album_art)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_track, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = tracks[position]
        holder.title.text = track.title
        holder.artist.text = track.artist
        if (track.albumArt != null) {
            holder.albumArt.setImageBitmap(track.albumArt)
        } else {
            holder.albumArt.setImageResource(R.drawable.ic_launcher)
        }
    }

    override fun getItemCount() = tracks.size
}