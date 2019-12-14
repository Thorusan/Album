package com.example.album.ui.users

import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import butterknife.ButterKnife
import com.example.album.R
import com.example.album.datamodel.AlbumData
import com.example.album.datamodel.UserData
import com.example.album.network.ApiService
import com.example.album.network.NetworkDataProvider
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_users.*
import org.json.JSONArray
import kotlin.math.log

class UsersActivity : AppCompatActivity(), UsersListViewPresenterContract.ViewInterface {
    private lateinit var usersPresenter: UsersListPresenter
    val apiservice by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        setSupportActionBar(toolbar)

        ButterKnife.bind(this)


        val dataProvider = NetworkDataProvider(apiservice)
        val model = UsersModel(dataProvider)

        usersPresenter = UsersListPresenter(this,  model)
        usersPresenter.getUsersList()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_users, menu)
        return true
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
       Log.d("USERS", usersList.toString());
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun onItemClick(album: AlbumData) {

    }
}
