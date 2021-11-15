package com.example.evergreen.View.sharedpreference

import android.annotation.SuppressLint
import android.content.Context

class SharedOTPCode(private val context: Context) {
    @SuppressLint("CommitPrefEdits")
    fun saveLogInState(Key : String, value : Boolean) {
        val sharedPreferences = context.getSharedPreferences("shared",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(Key,value)
            .apply()
    }
    fun getLogInstate(key: String, default: Boolean): Boolean {
        val sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key,default)
    }
}