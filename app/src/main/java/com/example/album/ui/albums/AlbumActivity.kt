package com.example.album.ui.albums

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.album.R

class AlbumActivity : AppCompatActivity() {

    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        getUserIdFromBundle()
    }


    private fun getUserIdFromBundle() {
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            userId = bundle.getInt("parUserId");
        }
    }
}
