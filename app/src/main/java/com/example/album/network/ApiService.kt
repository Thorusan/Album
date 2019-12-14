package com.example.album.network

import com.example.album.datamodel.UserData
import com.example.album.common.Constants.Companion.BASE_URL
import com.example.album.datamodel.AlbumData
import com.example.album.datamodel.PhotoData
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(BASE_URL + "/users")
    fun getUsers(): Observable<List<UserData.User>>

    @GET(BASE_URL + "/albums")
    fun getUserAlbums(@Query("userId") userId: Int): Observable<List<AlbumData>>

    @GET(BASE_URL + "/photos/")
    fun getAlbumPhotos(@Query("albumId") albumId: Int): Observable<List<PhotoData>>


    companion object {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }

}