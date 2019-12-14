package com.example.album.ui.albums


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.album.R
import com.example.album.datamodel.AlbumData


class AlbumListAdapter(private val albumList: List<AlbumData>, private val listener: (Int) -> Unit) : RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albumList[position], listener)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.text_name)
        lateinit var textName: TextView


        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(album: AlbumData, listener: (Int) -> Unit) = with(itemView) {

            textName.text = album.title

            itemView.setOnClickListener { listener(album.id) }
        }
    }
}