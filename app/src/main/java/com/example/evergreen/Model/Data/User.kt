package com.example.evergreen.Model.Data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*
@Entity(tableName = "Users")
data class User (
    @PrimaryKey(autoGenerate = true)
    var userId : Int ? = null,
    var userName : String? = null ,
    var dateOfBirth : String? = null ,
    var email : String? = null ,
    var phoneNumber : Int? = null ,
    var password : String? = null ,
): Serializable
