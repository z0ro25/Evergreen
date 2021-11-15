package com.example.evergreen.ViewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.evergreen.Model.Data.User
import com.example.evergreen.Model.Data.UserDataBase
import com.example.evergreen.View.Base.BaseViewModel
import com.example.evergreen.repository.SigUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigUpViewmodel @Inject constructor(private val sigUpRepository: SigUpRepository) : BaseViewModel() {
    var listDataUser: MutableLiveData<List<User>>
    var listUserPhoneNum: MutableLiveData<List<Int>>

    init {
        listDataUser = MutableLiveData()
        listUserPhoneNum = MutableLiveData()
    }

    fun getAllDataUserObservers(): MutableLiveData<List<User>> {
        val list = sigUpRepository.getlistUser()
        listDataUser.postValue(list)
        return listDataUser
    }

    fun getAllUserPhoneNum(): MutableLiveData<List<Int>> {
        val listNUm = sigUpRepository.getlistUserNumber()
        listUserPhoneNum.postValue(listNUm)
        return listUserPhoneNum
    }


    fun addNewUser(user: User) {
        sigUpRepository.addnewUser(user)
    }
}