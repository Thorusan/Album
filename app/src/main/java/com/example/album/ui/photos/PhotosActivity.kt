package com.example.album.ui.photos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.album.R
import com.example.album.datamodel.PhotoData

class PhotosActivity : AppCompatActivity() {

    private lateinit var photostList: ArrayList<PhotoData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)

        getPhotos();
    }

    private fun getPhotos() {
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            photostList = bundle.getParcelableArrayList("parPhotosList");
        }
    }
}
