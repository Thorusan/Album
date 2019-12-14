package com.example.album.ui.albums

import com.example.album.datamodel.AlbumData
import com.example.album.datamodel.UserData


interface AlbumListViewPresenterContract {
    interface ViewInterface {
        fun displayAlbumList(usersList: List<AlbumData>)
        fun showProgress()
        fun hideProgress()
        fun onItemClick(albumId: Int)
    }

    interface PresenterInterface {
        fun getAlbumList(userId: Int)
    }
}