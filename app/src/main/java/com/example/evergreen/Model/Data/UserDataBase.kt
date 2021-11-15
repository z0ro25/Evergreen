package com.example.evergreen.Model.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class],version = 1  )
abstract class UserDataBase : RoomDatabase(){
    abstract fun userDao() : UserDao

    companion object{
        private var db : UserDataBase? = null

        fun getUser(context: Context): UserDataBase {
            db = Room.databaseBuilder(context.applicationContext,UserDataBase::class.java,"database_name")
                .allowMainThreadQueries()
                .build()
            return db!!
        }
    }
}