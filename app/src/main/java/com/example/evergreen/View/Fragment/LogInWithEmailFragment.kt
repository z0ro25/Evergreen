package com.example.evergreen.View.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.evergreen.Model.Data.User
import com.example.evergreen.R
import com.example.evergreen.View.Base.BaseFragment
import com.example.evergreen.ViewModel.LoginViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.log_in_email_layout.*
@AndroidEntryPoint
class LogInWithEmailFragment : BaseFragment() {
    private var listUserFromDB: List<User> = listOf()
    private val PARTKEY = "PARTKEY"
    private var userNumber = 0
    private var userEmail = ""
    private val emailOTP = 9999
    lateinit var loginViewmodel : LoginViewmodel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.log_in_email_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            userNumber = it.getInt(PARTKEY)
        }
        btn_login_email.isEnabled = false
        edt_email_login.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                btn_login_email.isEnabled = true
            }
        })
        btn_login_email.setOnClickListener {
            userEmail = edt_email_login.text.toString()
            getListEmailDB()
            checkEmailUser()
        }
        btn_back_login_pass.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun getListEmailDB() {
        loginViewmodel = ViewModelProvider(this).get(LoginViewmodel::class.java)
        loginViewmodel.getAllUserDB().observe(viewLifecycleOwner, Observer {
            listUserFromDB = it
        })
    }

    private fun checkEmailUser() {
        listUserFromDB.forEach {
            if (userNumber == it.phoneNumber){
                if (userEmail == it.email) {
                    Toast.makeText(requireActivity(), "Log in complete", Toast.LENGTH_SHORT)
                    //open email verify fragment
                    requireActivity().supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fragment_enter_anim,R.anim.fragment_exit_anim)
                        .replace(R.id.frm_container,
                            VerifyOTPCodeFragment.newInstance(userNumber,emailOTP),
                            "VerifyEmailFragment")
                        .commit()
                } else {
                    Toast.makeText(requireActivity(), "your email is incorrect", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    companion object {
        fun newInstance(number: Int) =
            LogInWithEmailFragment().apply {
                arguments = Bundle().apply {
                    putInt(PARTKEY, number)
                }
            }
    }
}