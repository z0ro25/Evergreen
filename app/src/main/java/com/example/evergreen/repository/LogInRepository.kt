package com.example.evergreen.repository

import com.example.evergreen.Model.Data.User
import com.example.evergreen.Model.Data.UserDao
import com.example.evergreen.Model.apidata.HiepUser
import com.example.evergreen.callApi.ApiServise
import com.example.evergreen.callApi.PostUser
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class LogInRepository @Inject constructor(private val userDao: UserDao) {
    fun getallUserfromDb() : List<User>{
        return  userDao.getAllUser()
    }
    fun getAllEmail() : List<String> {
        return  userDao.getAllEmail()
    }
    suspend fun pushPost(user : HiepUser):Response<HiepUser>{
        return PostUser.post().create(ApiServise::class.java).postValue(user.grant_type,user.username,user.password,user.user_type)
    }
}