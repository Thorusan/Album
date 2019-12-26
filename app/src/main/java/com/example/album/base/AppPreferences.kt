package com.example.album.base

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "Preferences"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // list of app specific preferences
    private val USER_ID = Pair("userId", -1)
    private val ALBUM_ID = Pair("albumId", -1)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var userId: Int
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getInt(USER_ID.first, USER_ID.second)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putInt(USER_ID.first, value)
        }

    var albumId: Int
        get() = preferences.getInt(ALBUM_ID.first, ALBUM_ID.second)

        set(value) = preferences.edit {
            it.putInt(ALBUM_ID.first, value)
        }
}