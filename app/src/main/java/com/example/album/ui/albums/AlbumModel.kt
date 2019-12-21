package com.example.album.ui.albums


import com.example.album.datamodel.AlbumData
import com.example.album.datamodel.PhotoData
import com.example.album.datamodel.UserData
import com.example.album.network.DataProvider
import io.reactivex.Observable


class AlbumModel(val dataProvider: DataProvider) {
    fun getAlbumsFromProvider(userId: Int): Observable<List<AlbumData>> {
        return dataProvider.getAlbums(userId)
    }

    /**
     * Get photos for specified Album with albumId
     */
    fun getPhotosFromProvider(albumId: Int): Observable<List<PhotoData>> {
        return dataProvider.getPhotos(albumId)
    }

}