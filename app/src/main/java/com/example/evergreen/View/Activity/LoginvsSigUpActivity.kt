package com.example.evergreen.View.Activity

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import com.example.evergreen.R
import com.example.evergreen.View.Base.BaseActivity
import com.example.evergreen.View.Base.BaseFragment
import com.example.evergreen.View.Fragment.InsertPhoneNumFragment
import com.example.evergreen.View.sharedpreference.SharedOTPCode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginvsSigUpActivity : BaseActivity() {
    private val otpCode = 7777
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.phone_verify_layout)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
       supportFragmentManager.beginTransaction()
        .setCustomAnimations(R.anim.fragment_enter_anim,R.anim.fragment_exit_anim)
           .add(R.id.frm_container,InsertPhoneNumFragment.newInstance(otpCode),"InsertPhoneNumFragment")
           .commit()
    }
}