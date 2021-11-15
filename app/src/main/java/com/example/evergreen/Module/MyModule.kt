package com.example.evergreen.Module

import android.app.Application
import android.content.Context
import com.example.evergreen.Model.Data.UserDao
import com.example.evergreen.Model.Data.UserDataBase
import com.example.evergreen.Model.apidata.HiepUser
import com.example.evergreen.callApi.ApiServise
import com.example.evergreen.callApi.PostUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MyModule {
    @Singleton
    @Provides
    fun getUser( context: Application) : UserDataBase {
        return UserDataBase.getUser(context)
    }
    @Singleton
    @Provides
    fun getDataUser( user : UserDataBase) : UserDao {
        return user.userDao()
    }
    @Singleton
    @Provides
    fun push(retrofit: Retrofit) : ApiServise{
        return retrofit.create(ApiServise::class.java)
    }
}