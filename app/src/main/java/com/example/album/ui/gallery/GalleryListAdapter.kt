package com.example.album.ui.gallery


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.album.R
import com.example.album.common.GlideApp
import com.example.album.datamodel.PhotoData


class GalleryListAdapter(
    private var context: Context,
    private var photosList: List<PhotoData>,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<GalleryListAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photosList[position], listener)
    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.img_photo)
        lateinit var imgPhoto: ImageView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(photo: PhotoData, listener: (Int) -> Unit) = with(itemView) {

            /*  textName.text = album.title */

              GlideApp.with(this)
                  .load(photo.thumbnailUrl)
                  .into(imgPhoto)

            itemView.setOnClickListener { listener(photo.id) }
        }
    }
}