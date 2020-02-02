package com.example.album.ui.users

import android.R.attr.data
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import butterknife.BindView
import butterknife.ButterKnife
import com.example.album.R
import com.example.album.base.App
import com.example.album.base.AppPreferences
import com.example.album.datamodel.ListsData
import com.example.album.datamodel.UserData
import com.example.album.db.AppDatabase
import com.example.album.db.UserEntity
import com.example.album.network.NetworkDataProvider
import com.example.album.ui.albums.AlbumActivity
import com.example.album.ui.error.ErrorActivity
import com.example.album.utils.Utility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.io.Console
import java.util.*
import kotlin.collections.ArrayList


class UsersListActivity : AppCompatActivity(), UsersListViewPresenterContract.ViewInterface {

    @BindView(R.id.progress_circle)
    lateinit var progressView: ProgressBar
    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.toolbar)
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private lateinit var usersPresenter: UsersListPresenter

    private lateinit var db: AppDatabase

    private var listUsers = ArrayList<UserEntity>()
    private val compositeDisposable = CompositeDisposable()


    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        ButterKnife.bind(this)
        setSupportActionBar(toolbar)

        db = AppDatabase(this)


        val dataProvider = NetworkDataProvider(App.apiService!!)
        val model = UsersModel(dataProvider)

        usersPresenter = UsersListPresenter(this,  model)

        if (Utility.isNetworkConnected(this)) {
            // get users from database
            compositeDisposable.add(db.userDao().getAllUsers()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    listUsers.clear()
                    listUsers.addAll(it)
                    displayUsersEntityList(listUsers)

                    if (listUsers.isEmpty()) {
                        usersPresenter.getUsersList()
                    }
                });


        } else {
            val intent: Intent? = Intent(this, ErrorActivity::class.java);
            startActivity(intent);
        }
    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_users, menu)
        return true
    }

    @Override
    override fun onResume() {
        if (!Utility.isNetworkConnected(this))  {
            val intent: Intent? = Intent(this, ErrorActivity::class.java);
            startActivity(intent);
        }

        super.onResume()
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

    fun displayUsersEntityList(usersList: List<UserEntity>) {
        val listAdapter = UsersEntityListAdapter(
            ArrayList(usersList),
            { item -> usersPresenter.onItemClick(item) }
        )


        recyclerView.setLayoutManager(LinearLayoutManager(this))
        recyclerView.setItemAnimator(DefaultItemAnimator())
        // Binds the Adapter to the RecyclerView
        recyclerView.setAdapter(listAdapter)
    }

    override fun displayUsersList(usersList: List<UserData.User>) {
        ListsData.users = usersList

        val listAdapter = UsersListAdapter(
            ArrayList(usersList),
            { item -> usersPresenter.onItemClick(item) }
        )
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        recyclerView.setItemAnimator(DefaultItemAnimator())
        // Binds the Adapter to the RecyclerView
        recyclerView.setAdapter(listAdapter)


        val entityUsers: ArrayList<UserEntity> = ArrayList()
        usersList.forEach {
            entityUsers.add(UserEntity(it.id, it.name, it.username, it.email, it.phone))
        }

        if (listUsers.isEmpty()) {
            val thread = Thread {
                db.userDao().insertAll(entityUsers);
            }
            thread.start()
        }
    }

    override fun showProgress() {
        progressView.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressView.visibility = View.GONE
    }

    override fun displayErrorMessage() {
        Toast.makeText(this, getString(R.string.error),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onItemClick(userId: Int) {
        AppPreferences.userId = userId

        val intent: Intent? = Intent(this, AlbumActivity::class.java);
        if (intent != null) {
            intent.putExtra("parUserId", userId)
            startActivity(intent);
        }
    }

}
