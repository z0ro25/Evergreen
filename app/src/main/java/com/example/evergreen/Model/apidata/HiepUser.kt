package com.example.evergreen.Model.apidata

import java.io.Serializable

data class HiepUser(
    val grant_type : String,
    val username : String,
    val password : String,
    val user_type : String,
):Serializable
