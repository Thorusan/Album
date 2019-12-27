package com.example.album.ui.users

import com.example.album.datamodel.UserData


interface UsersListViewPresenterContract {
    interface ViewInterface {
        fun displayUsersList(usersList: List<UserData.User>)
        fun showProgress()
        fun hideProgress()
        fun displayErrorMessage()
        fun onItemClick(userId: Int)
    }

    interface PresenterInterface {
        fun getUsersList()
    }
}