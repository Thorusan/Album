package com.example.album.datamodel

import com.google.gson.annotations.SerializedName

class UserData() {
    data class User(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("username")
        val username: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("address")
        val address: Address,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("website")
        val website: String,
        @SerializedName("company")
        val company: Company
    )

    data class Address(
        @SerializedName("street")
        val street: String,
        @SerializedName("suite")
        val suite: String,
        @SerializedName("city")
        val city: String,
        @SerializedName("zipcode")
        val zipcode: String,
        @SerializedName("geo")
        val Geo: String
    )

    data class Geo(
        @SerializedName("lat")
        val latitude: Double,
        @SerializedName("lng")
        val longitude: Double
    )

    data class Company(
        @SerializedName("name")
        val name: String,
        @SerializedName("catchPhrase")
        val catchPhrase: String,
        @SerializedName("catchPhrase")
        val bs: String
    )
}



