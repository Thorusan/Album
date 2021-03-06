package com.example.album.ui.gallery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.album.R
import com.example.album.common.Constants.Companion.MIN_COLS_GALLERY
import com.example.album.datamodel.ListsData
import com.example.album.datamodel.PhotoData
import com.example.album.ui.photo.PhotoActivity
import com.example.album.utils.Utility

class GalleryActivity : AppCompatActivity() {


    @BindView(R.id.progress_circle)
    lateinit var progressView: ProgressBar
    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.toolbar)
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    @BindView(R.id.text_title_nav)
    lateinit var textTitle: TextView


    private lateinit var galleryListAdapter: GalleryListAdapter
    private val minFloatWidth = 120F
    private var albumId: Int = -1

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        getPhotos();
        setTitle()
        displayGallery();
    }

    private fun setTitle() {
        val album = ListsData.albums.find { it.id == albumId }!!
        textTitle.text  = album.title
    }

    @Override
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getPhotos() {
        showProgress()
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            ListsData.photos = bundle.getParcelableArrayList("parPhotosList");
            albumId = bundle.getInt("parAlbumId")
        }
    }

    fun displayGallery() {
       galleryListAdapter = GalleryListAdapter(this,
            ArrayList(ListsData.photos),
            { item -> onItemClick(item) }
        )

        var numCols: Int = Utility.calculateNoOfColumns(this, minFloatWidth)
        if (numCols < 2) numCols = MIN_COLS_GALLERY

        recyclerView.setLayoutManager(GridLayoutManager(getApplicationContext(),numCols))
        recyclerView.setItemAnimator(DefaultItemAnimator())
        // Binds the Adapter to the RecyclerView
        recyclerView.setAdapter(galleryListAdapter)
        hideProgress()

    }

    fun showProgress() {
        progressView.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressView.visibility = View.GONE
    }

    private fun onItemClick(item: PhotoData) {
        val intent: Intent? = Intent(this, PhotoActivity::class.java);
        if (intent != null) {
            intent.putExtra("parPhoto", item)
            startActivity(intent);
        }
    }
}
