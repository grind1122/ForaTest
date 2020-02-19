package com.grind.foratest.views

import com.grind.foratest.models.Album
import com.grind.foratest.models.Song

interface IAlbumInfoView {
    fun showInfo(album: Album?, songs: List<Song>)
}