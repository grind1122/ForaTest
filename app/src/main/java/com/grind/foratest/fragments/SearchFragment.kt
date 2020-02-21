package com.grind.foratest.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grind.foratest.models.Info
import com.grind.foratest.presenters.AlbumListPresenter
import com.grind.foratest.R
import com.grind.foratest.views.IAlbumListView
import com.grind.foratest.adapters.AlbumListAdapter
import com.grind.foratest.utils.ItemOffsetDecoration
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment(), IAlbumListView {
    private val compositeDisposable = CompositeDisposable()
    private lateinit var presenter: AlbumListPresenter

    private lateinit var rv : RecyclerView
    private lateinit var adapter: AlbumListAdapter

    private lateinit var etSearch: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = View.inflate(context, R.layout.fragment_album_search, null)
        rv = inflate.findViewById(R.id.rv_album_list)
        etSearch = inflate.findViewById(R.id.et_search)
        presenter = AlbumListPresenter(this)
        return inflate
    }

    override fun onStart() {
        super.onStart()
        adapter = AlbumListAdapter(object: AlbumListAdapter.AlbumItemClickListener{
            override fun itemClick(albumId: Int) {
                val bundle = Bundle()
                bundle.putInt("albumId",albumId)
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.main_container,
                        AlbumInfoFragment().apply {
                        arguments = bundle })
                    ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    ?.addToBackStack(this.javaClass.simpleName)
                    ?.commit()
            }
        })
        rv.adapter = adapter
        rv.layoutManager = GridLayoutManager(context,2, GridLayoutManager.VERTICAL, false)
        rv.addItemDecoration((ItemOffsetDecoration(context!!, R.dimen.grid_item_margin)))

        presenter.getAlbumsList("katy+perry")

        val searchSubscribe = RxTextView.afterTextChangeEvents(etSearch)
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .filter { !it.editable().isNullOrBlank() }
            .map { it.editable().toString().replace(' ', '+') }
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe({ presenter.getAlbumsList(it) },
                { Toast.makeText(context, "Search error", Toast.LENGTH_SHORT).show() })
        compositeDisposable.add(searchSubscribe)
    }

    override fun showAlbumList(list: List<Info>) {
        Log.e(this.javaClass.simpleName, "list presented, size ${list.size}")
        adapter.setItems(list)
    }

    override fun info(msg: String) {

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}