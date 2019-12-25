package com.example.album.ui.users

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.album.R
import com.example.album.base.App
import com.example.album.datamodel.UserData
import com.example.album.network.ApiService
import com.example.album.network.NetworkDataProvider
import com.example.album.ui.albums.AlbumActivity
import java.util.*

class UsersListActivity : AppCompatActivity(), UsersListViewPresenterContract.ViewInterface {

    @BindView(R.id.progress_circle)
    lateinit var progressView: ProgressBar
    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.toolbar)
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private lateinit var usersPresenter: UsersListPresenter


    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        ButterKnife.bind(this)
        setSupportActionBar(toolbar)

        val dataProvider = NetworkDataProvider(App.apiService!!)
        val model = UsersModel(dataProvider)

        usersPresenter = UsersListPresenter(this,  model)
        usersPresenter.getUsersList()

    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_users, menu)
        return true
    }

    @Override
    override fun onDestroy() {
        usersPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun displayUsersList(usersList: List<UserData.User>) {
        val listAdapter = UsersListAdapter(
            ArrayList(usersList),
            { item -> usersPresenter.onItemClick(item) }
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

    override fun onItemClick(userId: Int) {
        val intent: Intent? = Intent(this, AlbumActivity::class.java);
        if (intent != null) {
            intent.putExtra("parUserId", userId)
            startActivity(intent);
        }
    }

}
