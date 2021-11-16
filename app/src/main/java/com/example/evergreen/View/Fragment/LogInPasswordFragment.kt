package com.example.evergreen.View.Fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.evergreen.Model.Data.User
import com.example.evergreen.Model.apidata.HiepUser
import com.example.evergreen.R
import com.example.evergreen.View.Activity.HomeScreenActivity
import com.example.evergreen.View.Base.BaseFragment
import com.example.evergreen.View.sharedpreference.SharedOTPCode
import com.example.evergreen.ViewModel.LoginViewmodel
import com.example.evergreen.custumview.CustomeDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.log_in_email_layout.*
import kotlinx.android.synthetic.main.login_password_layout.*
import kotlinx.coroutines.*

@AndroidEntryPoint
class LogInPasswordFragment : BaseFragment() {
    private var listUser: List<User> = listOf()
    private var phoneNumBer = 0
    private var insertPassWord = ""
    private val isloggedin = true
    lateinit var loginViewmodel : LoginViewmodel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.login_password_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            phoneNumBer = it.getInt(KEYCODE)
        }
        btn_login_from_vrpass.isEnabled = false
        edt_login_password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                btn_login_from_vrpass.isEnabled = true
            }
        })
        btn_login_from_vrpass.setOnClickListener {
            insertPassWord = edt_login_password.text.toString()
            getListUserFromDB()
            checkPassWord()
            val user = HiepUser("password","hieptt123","123qwe","player")
            postValue(user)
        }
        imb_back_verifypass.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun postValue(user: HiepUser) {
        loginViewmodel = ViewModelProvider(this).get(LoginViewmodel::class.java)
        loginViewmodel.pushPost(user)
        loginViewmodel.mResposePost.observe(viewLifecycleOwner, Observer {
           Log.d("push",it.body().toString())
        })
    }

    private fun checkPassWord() {
        listUser.forEach {
            if (phoneNumBer == it.phoneNumber) {
                if (insertPassWord == it.password) {
                    Log.d("login","success")
                    val intent = Intent(requireActivity(), HomeScreenActivity::class.java)
                    saveToShared(SHAREDKEY,isloggedin)
                    startActivity(intent)
                }else {
                    Log.d("login","failed")
                    showDialog( R.id.frm_container,"The password you entered is incorrect.",
                        "Let’s try that again!",Color.RED)
                }
            }
        }
    }

    private fun saveToShared(Key : String,value: Boolean) {
        val sharedOTPCode = SharedOTPCode(requireActivity())
        sharedOTPCode.saveLogInState(Key,value)
    }

    private fun showDialog(id: Int,title: String, mess: String, color: Int) {
        GlobalScope.launch {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.dialog_enter_anim,R.anim.dialog_exit_anim)
                .add(id,
                    CustomeDialog.newInstance(title, mess, color), "erroDialog")
                .addToBackStack("erroDialog")
                .commit()
            delay(2000)
            requireActivity().supportFragmentManager.popBackStack("erroDialog",FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    private fun getListUserFromDB() {
        loginViewmodel = ViewModelProvider(this).get(LoginViewmodel::class.java)
        loginViewmodel.getAllUserDB().observe(viewLifecycleOwner, Observer {
            listUser = it
        })
    }

    companion object {
        private val KEYCODE: String = "KEYCODE"
         val SHAREDKEY = "SHAREDKEY"
        @JvmStatic
        fun newInstance(num: Int) =
            LogInPasswordFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEYCODE, num)
                }
            }
    }
}