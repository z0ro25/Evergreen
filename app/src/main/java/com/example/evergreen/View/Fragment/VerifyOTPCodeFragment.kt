package com.example.evergreen.View.Fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.evergreen.R
import com.example.evergreen.View.Activity.HomeScreenActivity
import com.example.evergreen.View.Base.BaseFragment
import com.example.evergreen.View.sharedpreference.SharedOTPCode
import com.example.evergreen.ViewModel.SigUpViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.verify_otp_layout.*

@AndroidEntryPoint
class VerifyOTPCodeFragment : BaseFragment() {
    private val userNumKEY: String = "key"
    private val OTP_PARSE_KEY: String = "OTP_PARSE_KEY"
    private var userDataNum: List<Int> = listOf()
    private var otpCode = ""
    private var otpLocal = 0
    private var userNumber = 0
    private val isloggedin = true
    lateinit var sigUpViewmodel : SigUpViewmodel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.verify_otp_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        code1_verify.inputType = InputType.TYPE_CLASS_NUMBER
        code2_verify.inputType = InputType.TYPE_CLASS_NUMBER
        code3_verify.inputType = InputType.TYPE_CLASS_NUMBER
        code4_verify.inputType = InputType.TYPE_CLASS_NUMBER
        code1_verify.requestFocus()
        btn_next_2.isEnabled = false
        code1_verify.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if (code1_verify.text.isNotEmpty()){
                    code2_verify.requestFocus()
                }
            }

        })
        code2_verify.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if (code2_verify.text.isNotEmpty()){
                    code3_verify.requestFocus()
                }else if (code2_verify.text.isNullOrEmpty()){
                    code1_verify.requestFocus()
                }
            }

        })
        code3_verify.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if (code3_verify.text.isNotEmpty()) {
                    code4_verify.requestFocus()
                } else code2_verify.requestFocus()
            }
        })
        code4_verify.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {

                if (code4_verify.text.isEmpty()){
                    code3_verify.requestFocus()
                    btn_next_2.isEnabled = false
                }else{
                    btn_next_2.isEnabled = true
                }
            }

        })


        arguments?.let {
            userNumber = it.getInt(userNumKEY)
            otpLocal = it.getInt(OTP_PARSE_KEY)
        }

        tv_number_user.text = userNumber.toString()
        Log.d("num", userNumber.toString())
        getNumberAndOTP()

        tv_loginwith_password.setOnClickListener {
           userDataNum.forEach {
               if (userNumber == it){
                   requireActivity().supportFragmentManager.beginTransaction()
                       .setCustomAnimations(R.anim.fragment_enter_anim,R.anim.fragment_exit_anim)
                       .replace(R.id.frm_container,
                           LogInPasswordFragment.newInstance(userNumber),
                           "c")
                       .addToBackStack("LogInPasswordFragment")
                       .commit()
               }
           }
        }

        tv_loginwith_email.setOnClickListener {
            userDataNum.forEach {
                if (userNumber == it){
                    requireActivity().supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fragment_enter_anim,R.anim.fragment_exit_anim)
                        .replace(R.id.frm_container,
                        LogInWithEmailFragment.newInstance(userNumber),
                        "LogInWithEmailFragment")
                        .addToBackStack("LogInWithEmailFragment")
                        .commit()
                }
            }

        }


        btn_next_2.setOnClickListener {
            if (code1_verify.text.isNullOrEmpty() || code2_verify.text.isNullOrEmpty() || code3_verify.text.isNullOrEmpty() || code4_verify.text.isNullOrEmpty()) {
                Toast.makeText(requireContext().applicationContext,
                    "your OTP is missing or emty",
                    Toast.LENGTH_SHORT).show()
            } else {
                otpCode =
                    code1_verify.text.toString() + code2_verify.text.toString() + code3_verify.text.toString() + code4_verify.text.toString()
                check()
            }
        }
    }

    private fun getNumberAndOTP() {
        sigUpViewmodel = ViewModelProvider(this).get(SigUpViewmodel::class.java)
        sigUpViewmodel.getAllUserPhoneNum().observe(viewLifecycleOwner, Observer {
            userDataNum = it
        })
    }

    private fun check() {

        val userCode = Integer.parseInt(otpCode)
        Log.d("userCode", userCode.toString())
        Log.d("localCode", otpLocal.toString())

        //check number
        if (userDataNum.isEmpty()) {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_enter_anim,R.anim.fragment_exit_anim)
                .replace(R.id.frm_container,
                    SigUpFragment.newInstance(userNumber),
                    "SigUpFragment")
                .addToBackStack("SigUpFragment")
                .commit()
        } else {
            userDataNum.forEach {
                if (userNumber == it) {
                    if (otpLocal == userCode) {
                        //login complete
                        val intent = Intent(requireActivity(), HomeScreenActivity::class.java)
                        saveToShared(LogInPasswordFragment.SHAREDKEY,isloggedin)
                        startActivity(intent)
                    } else {
                        Toast.makeText(requireContext().applicationContext,
                            "your OTP is invalidate",
                            Toast.LENGTH_SHORT).show()
                    }

                } else {
                    if (otpLocal == userCode) {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.fragment_enter_anim,R.anim.fragment_exit_anim)
                            .replace(R.id.frm_container,
                                SigUpFragment.newInstance(userNumber),
                                "SigUpFragment")
                            .addToBackStack("SigUpFragment")
                            .commit()
                    } else {
                        Toast.makeText(requireContext().applicationContext,
                            "your OTP is invalidate",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun saveToShared(Key : String,value: Boolean) {
        val sharedOTPCode = SharedOTPCode(requireActivity())
        sharedOTPCode.saveLogInState(Key,value)
    }

    override fun onResume() {
        super.onResume()
        code1_verify.requestFocus()
    }


    companion object {

        @JvmStatic
        fun newInstance(number: Int, code: Int) =
            VerifyOTPCodeFragment().apply {
                arguments = Bundle().apply {
                    putInt(userNumKEY, number)
                    putInt(OTP_PARSE_KEY, code)
                }
            }
    }


}