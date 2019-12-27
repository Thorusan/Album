package com.example.album.ui.error

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.album.R

class ErrorActivity : AppCompatActivity() {
    @BindView(R.id.btn_go_home)
    lateinit var btnHome: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)

        ButterKnife.bind(this)

        btnHome.setOnClickListener {
            this.finish()
        }
    }
}
