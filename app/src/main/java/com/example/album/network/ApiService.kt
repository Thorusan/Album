package com.example.album.network

import com.example.album.datamodel.UserData
import com.example.album.common.Constants.Companion.BASE_URL
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(BASE_URL + "/users")
    fun getUsers(): Observable<Response<List<UserData.User>>>


}