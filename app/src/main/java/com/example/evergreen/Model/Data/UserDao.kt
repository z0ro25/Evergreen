package com.example.evergreen.Model.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert()
    fun addNewUser(user : User)

    @Query("SELECT * FROM Users ORDER BY userId DESC ")
    fun getAllUser(): List<User>

    @Query("SELECT phoneNumber FROM Users")
    fun getAllPhoneNumber() : List<Int>

    @Query("SELECT email FROM Users")
    fun getAllEmail() : List<String>
//    @Query("DELETE FROM Users WHERE userId ")
//    fun deleteUser(user: User)
}