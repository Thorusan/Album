package com.example.album.ui.albums

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.album.R
import com.example.album.datamodel.AlbumData
import com.example.album.datamodel.PhotoData
import com.example.album.network.ApiService
import com.example.album.network.NetworkDataProvider
import java.util.*


class AlbumActivity : AppCompatActivity(), AlbumListViewPresenterContract.ViewInterface {
    @BindView(R.id.progress_circle)
    lateinit var progressView: ProgressBar
    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.toolbar)
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private lateinit var albumPresenter: AlbumListPresenter

    private lateinit var albumList: List<AlbumData>
    private lateinit var photosList: List<PhotoData>
    private lateinit var albumsListAdapter: AlbumListAdapter

    private val photoThumbnails: HashMap<Int, List<PhotoData>> = HashMap()

    private var userId: Int = -1

    val apiservice by lazy {
        ApiService.create()
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true);

        getUserIdFromBundle()

        val dataProvider = NetworkDataProvider(apiservice)
        val model = AlbumModel(dataProvider)

        albumPresenter = AlbumListPresenter(this, model)
        albumPresenter.getAlbumList(userId);
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
        this.albumList = albumList;


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
        photoThumbnails.put(albumId, photosList)

        val randomPhoto: PhotoData = photosList.shuffled().take(1)[0]

        albumList.find { it.id == albumId }!!.albumThumbnail = randomPhoto.thumbnailUrl

        albumsListAdapter.update(albumList)

        Log.d("TAG", photosList.toString());
    }

    override fun showProgress() {
        progressView.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressView.visibility = View.GONE
    }

    override fun onItemClick(albumId: Int) {
        // TODO
        /*val intent: Intent? = Intent(this, AlbumActivity::class.java);
        if (intent != null) {
            intent.putExtra("parUserId", userId)
            startActivity(intent);
        }*/
    }
}
