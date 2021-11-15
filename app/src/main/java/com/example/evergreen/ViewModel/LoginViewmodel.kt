package com.example.evergreen.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.evergreen.Model.Data.User
import com.example.evergreen.Model.Data.UserDataBase
import com.example.evergreen.Model.apidata.HiepUser
import com.example.evergreen.View.Base.BaseViewModel
import com.example.evergreen.repository.LogInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor (val logInRepository: LogInRepository) : BaseViewModel() {
    var listUserEmail : MutableLiveData<List<String>>
    var listUser : MutableLiveData<List<User>>
    var mResposePost : MutableLiveData<Response<HiepUser>>

    init {
        mResposePost = MutableLiveData()
        listUserEmail = MutableLiveData()
        listUser = MutableLiveData()
    }
     fun getAllUserDB() : MutableLiveData<List<User>> {
         val lus = logInRepository.getallUserfromDb()
         listUser.postValue(lus)
         return listUser
     }
    fun getUserEmailFromDB() : MutableLiveData<List<String>>{
            val lUser = logInRepository.getAllEmail()
            listUserEmail.postValue(lUser)
        return listUserEmail
    }
    fun pushPost(user: HiepUser) {
        GlobalScope.launch {
            val respon = logInRepository.pushPost(user)
            mResposePost.postValue(respon)
        }
    }
}