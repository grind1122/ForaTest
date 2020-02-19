package com.grind.foratest.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import com.grind.foratest.R
import com.grind.foratest.models.Album
import com.grind.foratest.models.Song
import com.grind.foratest.presenters.AlbumInfoPresenter
import com.grind.foratest.retrofit.ITunesApi
import com.grind.foratest.retrofit.MyRetrofit
import com.grind.foratest.views.IAlbumInfoView
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AlbumInfoFragment : Fragment(), IAlbumInfoView {
    private lateinit var label: ImageView
    private lateinit var albumName: TextView
    private lateinit var artistName: TextView
    private lateinit var genre: TextView
    private lateinit var year: TextView
    private lateinit var songCount: TextView
    private lateinit var songContainer: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = View.inflate(context, R.layout.fragment_album_info, null)
        label = v.findViewById(R.id.imv_label)
        albumName = v.findViewById(R.id.tv_album_name)
        artistName = v.findViewById(R.id.tv_artist)
        genre = v.findViewById(R.id.tv_genre)
        year = v.findViewById(R.id.tv_release_year)
        songCount = v.findViewById(R.id.tv_song_count)
        songContainer = v.findViewById(R.id.ll_song_container)

        val presenter = AlbumInfoPresenter(this)
        presenter.getAlbumInfo(arguments?.getInt("albumId")?: 0)
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
            genre.text = album.primaryGenreName
            year.text = album.releaseDate.substring(0..3)
            songCount.text = "Playlist of ${album.trackCount} songs:"

            for (i in 1..songs.size) {
                val holder = TextView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        topMargin = 20
                        bottomMargin = 20
                    }
                }
                holder.text = "$i. ${songs[i].trackName}"
                songContainer.addView(holder)
            }

        }

    }
}