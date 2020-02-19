package com.grind.foratest.views

import com.grind.foratest.models.Info

interface IAlbumListView {
    fun showAlbumList(list: List<Info>)
    fun info(msg: String)
}