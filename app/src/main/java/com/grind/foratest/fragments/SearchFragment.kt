package com.grind.foratest.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
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
    private val presenter = AlbumListPresenter(this)

    private lateinit var rv: RecyclerView
    private lateinit var adapter: AlbumListAdapter
    private lateinit var lm: RecyclerView.LayoutManager

    private lateinit var etSearch: EditText
    private lateinit var dropSearch: ImageView
    private lateinit var emptySearch: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = View.inflate(context, R.layout.fragment_album_search, null)
        rv = inflate.findViewById(R.id.rv_album_list)
        etSearch = inflate.findViewById(R.id.et_search)
        dropSearch = inflate.findViewById(R.id.imv_search_drop)
        emptySearch = inflate.findViewById(R.id.ll_search_empty)

        lm = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        if (savedInstanceState != null) {
            lm.onRestoreInstanceState(savedInstanceState.getParcelable("lmState"))
        }
        rv.layoutManager = lm
        return inflate
    }

    override fun onStart() {
        super.onStart()

        adapter = AlbumListAdapter(object : AlbumListAdapter.AlbumItemClickListener {
            //when some album has been clicked fragment transaction started
            override fun itemClick(albumId: Int) {
                val bundle = Bundle()
                bundle.putInt("albumId", albumId)
                fragmentManager?.beginTransaction()
                    ?.add(R.id.main_container,
                        AlbumInfoFragment().apply {
                            arguments = bundle
                        })
                    ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    ?.addToBackStack(this.javaClass.simpleName)
                    ?.commit()
            }
        })
        rv.adapter = adapter
        rv.addItemDecoration((ItemOffsetDecoration(context!!, R.dimen.grid_item_margin)))

        presenter.getAlbumsList("beatles")

        dropSearch.setOnClickListener {
            etSearch.setText("")
        }

        //textChangeListener for search
        val searchSubscribe = RxTextView.textChanges(etSearch)
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .filter { it.isNotEmpty() }
            .map { it.toString().replace(' ', '+') }
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe({
                presenter.getAlbumsList(it)
            },
                { Log.e(this.javaClass.simpleName, it.message) })
        compositeDisposable.add(searchSubscribe)
    }

    override fun showAlbumList(list: List<Info>) {
        Log.e(this.javaClass.simpleName, "list presented, size ${list.size}")
        if (list.isNotEmpty()) {
            rv.visibility = View.VISIBLE
            emptySearch.visibility = View.GONE
            adapter.setItems(list)
            rv.scrollToPosition(0)
        } else {
            rv.visibility = View.INVISIBLE
            emptySearch.visibility = View.VISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("lmState", lm.onSaveInstanceState())
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.clearResources()
        compositeDisposable.clear()
    }

}