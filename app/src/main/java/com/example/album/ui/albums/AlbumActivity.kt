package com.example.album.ui.albums

import android.content.Intent
import android.os.Bundle
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
import com.example.album.network.ApiService
import com.example.album.network.NetworkDataProvider
import com.example.album.ui.users.UsersListAdapter
import java.util.ArrayList

class AlbumActivity : AppCompatActivity(), AlbumListViewPresenterContract.ViewInterface {
    @BindView(R.id.progress_circle)
    lateinit var progressView: ProgressBar
    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.toolbar)
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private lateinit var albumPresenter: AlbumListPresenter

    private var userId: Int = -1

    val apiservice by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        ButterKnife.bind(this)
        setSupportActionBar(toolbar)

        getUserIdFromBundle()

        val dataProvider = NetworkDataProvider(apiservice)
        val model = AlbumModel(dataProvider)

        albumPresenter = AlbumListPresenter(this, model)
        albumPresenter.getAlbumList(userId);
    }


    private fun getUserIdFromBundle() {
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            userId = bundle.getInt("parUserId");
        }
    }

    override fun displayAlbumList(usersList: List<AlbumData>) {
        val listAdapter = AlbumListAdapter(
            ArrayList(usersList),
            { item -> albumPresenter.onItemClick(item) }
        )
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        recyclerView.setItemAnimator(DefaultItemAnimator())
        // Binds the Adapter to the RecyclerView
        recyclerView.setAdapter(listAdapter)
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
