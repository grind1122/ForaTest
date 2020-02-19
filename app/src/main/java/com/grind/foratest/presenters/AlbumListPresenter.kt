package com.grind.foratest.presenters

import android.util.Log
import com.grind.foratest.retrofit.ITunesApi
import com.grind.foratest.retrofit.MyRetrofit
import com.grind.foratest.views.IAlbumListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class AlbumListPresenter(view: IAlbumListView) : IAlbumListPresenter {

    private val cd = CompositeDisposable()
    private val view = view
    private val api = MyRetrofit.getInstance().create(ITunesApi::class.java)

    override fun getAlbumsList(searchSequence: String) {
        var counter = 0
        val disposable = api.searchAlbum(searchSequence, "album")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response ->
                view.showAlbumList(response.infoList)
                response.infoList.forEach {
                    view.info("${counter++}) ${it.artistName} - ${it.collectionName} (${it.wrapperType} id - ${it.collectionId})")

                }},
                {Log.e("Presenter", it.message?: "Some Error")})
        cd.add(disposable)

    }

    override fun clearResources() {
        cd.clear()
    }
}
