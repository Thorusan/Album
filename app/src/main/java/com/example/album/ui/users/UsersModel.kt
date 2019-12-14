package com.example.album.ui.users


import com.example.album.datamodel.UserData
import com.example.album.network.DataProvider
import io.reactivex.Observable


class UsersModel(val dataProvider: DataProvider) {
    fun getUsersFromProvider(): Observable<List<UserData.User>> {
        return dataProvider.getUsers()
    }


}