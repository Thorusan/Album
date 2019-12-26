package com.example.album.base

import android.app.Application
import com.example.album.network.ApiService

val apiservice by lazy {
    ApiService.create()
}

class App : Application() {
    companion object {
        var apiService: ApiService? = apiservice
    }

    @Override
    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
    }
}