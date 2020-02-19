package com.grind.foratest.presenters

interface IAlbumListPresenter {
    fun getAlbumsList(searchSequence: String)
    fun clearResources()
}