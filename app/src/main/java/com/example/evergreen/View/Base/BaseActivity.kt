package com.example.evergreen.View.Base

import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    fun backRoot() {
        super.onBackPressed()
    }
}