package com.grind.foratest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.grind.foratest.R
import com.grind.foratest.models.Album
import com.grind.foratest.models.Song
import com.grind.foratest.presenters.AlbumInfoPresenter
import com.grind.foratest.utils.BackListener
import com.grind.foratest.views.IAlbumInfoView
import com.squareup.picasso.Picasso

class AlbumInfoFragment : Fragment(), IAlbumInfoView {
    private lateinit var backButton: ImageView
    private lateinit var label: ImageView
    private lateinit var albumName: TextView
    private lateinit var artistName: TextView
    private lateinit var genreAndYear: TextView
    private lateinit var songContainer: LinearLayout
    private val presenter = AlbumInfoPresenter(this)

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

        presenter.getAlbumInfo(arguments?.getInt("albumId") ?: 0)
        return v
    }

    //invoke when AlbumListPresenter get info about album
    override fun showInfo(album: Album?, songs: List<Song>) {
        if (album != null) {
            Picasso.get().load(album.artworkUrl100)
                .fit()
                .centerCrop()
                .into(label)

            albumName.text = album.collectionName
            artistName.text = album.artistName
            genreAndYear.text = "${album.primaryGenreName} | ${album.releaseDate.substring(0..3)}"

            //check single disc in collection or not
            var singleDisc = true
            for (song in songs) {
                if (song.discNumber > 1) {
                    singleDisc = false
                    break
                }
            }

            for (i in 0..songs.size) {
                val currSong = songs[i]
                songContainer.addView(
                    createSongItem(
                        currSong.discNumber,
                        currSong.trackNumber,
                        currSong.trackName,
                        currSong.trackTimeMillis / 1000,
                        singleDisc
                    )
                )
            }

        }

    }

    //inflate view for song and set fields
    private fun createSongItem(
        discNumber: Int,
        songNumber: Int,
        songName: String,
        seconds: Int,
        singleDisc: Boolean
    ): View {
        val v = View.inflate(context, R.layout.item_song, null).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        val number = v.findViewById<TextView>(R.id.tv_track_number)
        val name = v.findViewById<TextView>(R.id.tv_track_name)
        val time = v.findViewById<TextView>(R.id.tv_track_time)
        if (singleDisc) {
            number.text = "$songNumber"
        } else {
            number.text = "$discNumber.$songNumber"
        }
        name.text = songName
        time.text = String.format("%d:%02d", seconds / 60, seconds % 60)

        return v
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearResources()
    }

}