package com.example.album.ui.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.album.R
import com.example.album.datamodel.PhotoData

class GalleryActivity : AppCompatActivity() {

    @BindView(R.id.progress_circle)
    lateinit var progressView: ProgressBar
    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.toolbar)
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private lateinit var galleryListAdapter: GalleryListAdapter
    private lateinit var photostList: ArrayList<PhotoData>

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true);

        getPhotos();
        displayGallery();
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
            photostList = bundle.getParcelableArrayList("parPhotosList");
        }
    }

    fun displayGallery() {
       galleryListAdapter = GalleryListAdapter(this,
            ArrayList(this.photostList),
            { item -> onItemClick(item) }
        )
        recyclerView.setLayoutManager(GridLayoutManager(getApplicationContext(), 2))
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

    private fun onItemClick(item: Int) {

    }
}
