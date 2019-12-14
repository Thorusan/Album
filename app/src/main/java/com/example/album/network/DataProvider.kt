package com.example.album.network

import com.example.album.datamodel.AlbumData
import com.example.album.datamodel.PhotoData
import com.example.album.datamodel.UserData
import io.reactivex.Observable

interface DataProvider {
    fun getUsers(): Observable<List<UserData.User>>
    fun getAlbums(userId: Int): Observable<List<AlbumData>>
    fun getPhotos(albumId: Int): Observable<List<PhotoData>>
}

