package com.example.album.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") var id: Int


    )

{
}