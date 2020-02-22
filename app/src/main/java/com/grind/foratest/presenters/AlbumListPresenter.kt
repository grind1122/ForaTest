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
        val disposable = api.searchAlbum(searchSequence, "album", 200)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                view.showAlbumList(response.infoList.sortedBy { it.collectionName })
            },
                { Log.e("Presenter", it.message ?: "Some Error") })
        cd.add(disposable)

    }

    override fun clearResources() {
        cd.clear()
    }
}
