package com.example.album.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.album.datamodel.UserData


@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "username") var username: String,
    @ColumnInfo(name = "email") var email: String,
 /*   @ColumnInfo(name = "address") var address: String,*/
    @ColumnInfo(name = "phone") var phone: String
/*    @ColumnInfo(name = "website") var website: String,
    @ColumnInfo(name = "company") var company: UserData.Company*/

) {
}