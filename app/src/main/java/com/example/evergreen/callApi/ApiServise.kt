package com.example.evergreen.callApi


import com.example.evergreen.Model.apidata.HiepUser
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiServise {
    @FormUrlEncoded
    @POST("oauth/token")
   suspend fun postValue(
        @Field("grant_type") grant_type: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("user_type") user_type: String,
    ) : Response<HiepUser>
}