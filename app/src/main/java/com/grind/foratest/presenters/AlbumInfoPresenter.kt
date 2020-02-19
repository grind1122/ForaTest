package com.grind.foratest.presenters

import android.util.Log
import com.grind.foratest.models.Album
import com.grind.foratest.models.Song
import com.grind.foratest.retrofit.ITunesApi
import com.grind.foratest.retrofit.MyRetrofit
import com.grind.foratest.views.IAlbumInfoView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AlbumInfoPresenter(view: IAlbumInfoView) : IAlbumInfoPresenter {

    private val cd = CompositeDisposable()
    private val view = view
    private val api = MyRetrofit.getInstance().create(ITunesApi::class.java)

    override fun getAlbumInfo(albumId: Int) {
        val disposable = api.getAlbumInfo(albumId, "song")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    var album: Album? = null
                    val songs = mutableListOf<Song>()
                    it.infoList.forEach {info ->
                        if (info.wrapperType == "collection") {
                            album = info.toAlbum()
                        } else if(info.wrapperType == "track" && info.kind == "song"){
                            songs.add(info.toSong())
                        }
                    }
                    view.showInfo(album, songs)
                },
                { Log.e(this.javaClass.simpleName, it.message) })
        cd.add(disposable)
    }

    override fun clearResources() {
        cd.clear()
    }
}