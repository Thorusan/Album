package com.example.album.ui.albums

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.album.R
import com.example.album.base.App
import com.example.album.base.AppPreferences
import com.example.album.datamodel.AlbumData
import com.example.album.datamodel.ListsData
import com.example.album.datamodel.PhotoData
import com.example.album.network.ApiService
import com.example.album.network.NetworkDataProvider
import com.example.album.ui.error.ErrorActivity
import com.example.album.ui.gallery.GalleryActivity
import com.example.album.utils.Utility
import java.util.*
import kotlin.collections.ArrayList


class AlbumActivity : AppCompatActivity(), AlbumListViewPresenterContract.ViewInterface {
    @BindView(R.id.progress_circle)
    lateinit var progressView: ProgressBar
    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.toolbar)
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    @BindView(R.id.text_title_nav)
    lateinit var textTitle: TextView

    private lateinit var albumPresenter: AlbumListPresenter
    private lateinit var albumsListAdapter: AlbumListAdapter

    private val photoThumbnails: HashMap<Int, ArrayList<PhotoData>> = HashMap()

    private var userId: Int = -1


    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        getUserIdFromBundle()
        setTitle()

        val dataProvider = NetworkDataProvider(App.apiService!!)
        val model = AlbumModel(dataProvider)

        albumPresenter = AlbumListPresenter(this, model)

        if (Utility.isNetworkConnected(this)) {
            albumPresenter.getAlbumList(userId);
        } else {
            val intent: Intent? = Intent(this, ErrorActivity::class.java);
            startActivity(intent);
        }
    }

    @Override
    override fun onResume() {
        if (!Utility.isNetworkConnected(this))  {
            val intent: Intent? = Intent(this, ErrorActivity::class.java);
            startActivity(intent);
        } else {
            if (ListsData.albums.size == 0) {
                albumPresenter.getAlbumList(userId);
            }
        }
        super.onResume()
    }

    private fun setTitle() {
        val user = ListsData.users.find { it.id == userId }!!
        textTitle.text  = user.name
    }

    @Override
    override fun onDestroy() {
        albumPresenter.onDestroy()
        super.onDestroy()
    }

    @Override
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getUserIdFromBundle() {
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            userId = bundle.getInt("parUserId");
        }
    }

    override fun displayAlbumList(albumList: List<AlbumData>) {
        ListsData.albums = albumList;

        albumsListAdapter = AlbumListAdapter(this,
            ArrayList(albumList),
            { item -> albumPresenter.onItemClick(item) }
        )
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        recyclerView.setItemAnimator(DefaultItemAnimator())
        // Binds the Adapter to the RecyclerView
        recyclerView.setAdapter(albumsListAdapter)

    }

    override fun displayAlbumThumbnails(albumId: Int, photosList: List<PhotoData>) {
        photoThumbnails.put(albumId, ArrayList(photosList))

        val randomPhoto: PhotoData = photosList.shuffled().take(1)[0]
        ListsData.albums.find { it.id == albumId }!!.albumThumbnail = randomPhoto.thumbnailUrl
        albumsListAdapter.update(ListsData.albums)

        Log.d("TAG", photosList.toString());
    }

    override fun showProgress() {
        progressView.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressView.visibility = View.GONE
    }

    override fun onItemClick(albumId: Int) {
        AppPreferences.albumId = albumId

        val intent: Intent? = Intent(this, GalleryActivity::class.java);
        if (intent != null) {
            intent.putParcelableArrayListExtra("parPhotosList", photoThumbnails.get(albumId))
            intent.putExtra("parAlbumId", albumId)
            startActivity(intent);
        }
    }
}
