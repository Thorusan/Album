package com.example.album.network

import com.example.album.datamodel.UserData
import com.example.album.common.Constants.Companion.BASE_URL
import com.example.album.datamodel.AlbumData
import com.example.album.datamodel.PhotoData
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(BASE_URL + "/users")
    fun getUsers(): Observable<Response<List<UserData.User>>>

    @GET(BASE_URL + "/albums")
    fun getUserAlbums(@Query("userId") userId: Int): Observable<Response<List<AlbumData>>>

    @GET(BASE_URL + "/photos ")
    fun getAlbumPhotos(@Query("albumId") albumId: Int): Observable<Response<List<PhotoData>>>

}