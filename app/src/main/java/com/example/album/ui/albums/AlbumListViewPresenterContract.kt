package com.example.album.ui.albums

import com.example.album.datamodel.AlbumData
import com.example.album.datamodel.PhotoData
import com.example.album.datamodel.UserData


interface AlbumListViewPresenterContract {
    interface ViewInterface {
        fun displayAlbumList(usersList: List<AlbumData>)
        fun displayAlbumThumbnails(albumId: Int, photosList: List<PhotoData>)
        fun showProgress()
        fun hideProgress()
        fun onItemClick(albumId: Int)
    }

    interface PresenterInterface {
        fun getAlbumList(userId: Int)
        fun getPhotoList(albumId: Int)
    }
}