package com.example.album.network

import com.example.album.datamodel.AlbumData
import com.example.album.datamodel.PhotoData
import com.example.album.datamodel.UserData
import io.reactivex.Observable


class NetworkDataProvider(private var apiService: ApiService) :
    DataProvider {
    override fun getUsers(): Observable<List<UserData.User>> {
       return apiService.getUsers()
    }

    override fun getAlbums(userId: Int): Observable<List<AlbumData>> {
        return apiService.getUserAlbums(userId);
    }

    override fun getPhotos(albumId: Int): Observable<List<PhotoData>> {
        return apiService.getAlbumPhotos(albumId)
    }
}