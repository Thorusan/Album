package com.example.album.datamodel

import com.google.gson.annotations.SerializedName

data class AlbumData(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    var albumThumbnail: String


)





