package com.grind.foratest.fragments

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import com.grind.foratest.R
import com.grind.foratest.models.Album
import com.grind.foratest.models.Song
import com.grind.foratest.presenters.AlbumInfoPresenter
import com.grind.foratest.utils.BackListener
import com.grind.foratest.utils.DpPxUlit
import com.grind.foratest.views.IAlbumInfoView
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.util.*

class AlbumInfoFragment : Fragment(), IAlbumInfoView {
    private lateinit var backButton: ImageView
    private lateinit var label: ImageView
    private lateinit var albumName: TextView
    private lateinit var artistName: TextView
    private lateinit var genreAndYear: TextView
    private lateinit var songContainer: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = View.inflate(context, R.layout.fragment_album_info, null)
        backButton = v.findViewById(R.id.imv_back)
        label = v.findViewById(R.id.imv_label)
        albumName = v.findViewById(R.id.tv_album_name)
        artistName = v.findViewById(R.id.tv_artist)
        genreAndYear = v.findViewById(R.id.tv_genre_and_year)
        songContainer = v.findViewById(R.id.ll_song_container)

        backButton.setOnClickListener(BackListener(fragmentManager!!))

        val presenter = AlbumInfoPresenter(this)
        presenter.getAlbumInfo(arguments?.getInt("albumId") ?: 0)
        return v
    }


    override fun showInfo(album: Album?, songs: List<Song>) {
        if (album != null) {
            Picasso.get().load(album.artworkUrl100)
                .fit()
                .centerCrop()
                .into(label)

            albumName.text = album.collectionName
            artistName.text = album.artistName
            genreAndYear.text = "${album.primaryGenreName} | ${album.releaseDate.substring(0..3)}"

            for (i in 0..songs.size) {
                val currSong = songs[i]
                songContainer.addView(createSongItem(currSong.trackNumber, currSong.trackName, currSong.trackTimeMillis / 1000))
            }

        }

    }

    private fun createSongItem(songNumber: Int, songName: String, seconds: Int): View {
        val v = View.inflate(context, R.layout.item_song, null).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        val number = v.findViewById<TextView>(R.id.tv_track_number)
        val name = v.findViewById<TextView>(R.id.tv_track_name)
        val time = v.findViewById<TextView>(R.id.tv_track_time)
        number.text = songNumber.toString()
        name.text = songName
        time.text = "${seconds / 60}:${seconds % 60}"

        return v
    }

}