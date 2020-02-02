package com.example.album.db

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Observable

@Dao
interface UserDao {
    @Transaction
    fun insertAll(objects: List<UserEntity>) = objects.forEach {
        addUser(it)
    }


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: UserEntity): Long

    @Update
    fun updateUser(user: UserEntity): Int

    @Delete
    fun deleteUser(user: UserEntity): Int

    @Query("DELETE FROM user_table")
    fun deleteAllUsers()

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): Observable<List<UserEntity>>
}