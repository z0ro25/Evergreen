package com.example.evergreen.repository

import com.example.evergreen.Model.Data.User
import com.example.evergreen.Model.Data.UserDao
import javax.inject.Inject

class SigUpRepository @Inject constructor (private val userdao: UserDao) {
    fun getlistUser() : List<User>{
        return userdao.getAllUser()
    }
    fun getlistUserNumber() : List<Int>{
        return userdao.getAllPhoneNumber()
    }
    fun addnewUser(user: User) {
        userdao.addNewUser(user)
    }
}