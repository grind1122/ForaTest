package com.grind.foratest.adapters

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grind.foratest.models.Info
import com.grind.foratest.R
import com.grind.foratest.customviews.SquareImageView
import com.squareup.picasso.Picasso

class AlbumListAdapter(listener: AlbumItemClickListener) :
    RecyclerView.Adapter<AlbumListAdapter.AlbumHolder>() {

    private var itemList: MutableList<Info> = mutableListOf()
    private val listener = listener


    class AlbumHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val label: SquareImageView = itemView.findViewById(R.id.imv_label)
        val artist: TextView = itemView.findViewById(R.id.tv_artist)
        val albumName: TextView = itemView.findViewById(R.id.tv_album_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumHolder {
        val view = View.inflate(parent.context, R.layout.item_album, null)
            .apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }
        return AlbumHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: AlbumHolder, position: Int) {
        val album = itemList[position]
        loadImage(album.artworkUrl100, holder.label)
        holder.artist.text = album.artistName
        holder.albumName.text = album.collectionName
        holder.itemView.setOnClickListener { listener.itemClick(album.collectionId) }
    }

    fun addItems(items: List<Info>) {
        val startPosition = itemList.size - 1
        itemList.addAll(items)
        notifyItemRangeInserted(startPosition, items.size)

    }

    fun clearItems() {
        itemList.clear()
        notifyDataSetChanged()
    }

    fun setItems(items: List<Info>) {
        itemList = items.toMutableList()
        notifyDataSetChanged()
    }

    private fun loadImage(url: String, container: ImageView) {
        Picasso.get().load(Uri.parse(url))
            .fit()
            .centerCrop()
            .into(container)
    }

    interface AlbumItemClickListener {
        fun itemClick(albumId: Int)
    }

}