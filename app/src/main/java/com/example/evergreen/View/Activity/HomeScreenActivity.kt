package com.example.evergreen.View.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import com.example.evergreen.R
import com.example.evergreen.View.Base.BaseActivity
import com.example.evergreen.View.Fragment.LogInPasswordFragment.Companion.SHAREDKEY
import com.example.evergreen.View.sharedpreference.SharedOTPCode
import com.example.evergreen.custumview.CustomeDialog
import kotlinx.android.synthetic.main.home_screen_layout.*
import kotlinx.android.synthetic.main.nav_user.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeScreenActivity : BaseActivity() {
    lateinit var bar: ActionBarDrawerToggle
    private var isLogedin = false
    private val sharedOTPCode = SharedOTPCode(this)
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen_layout)

        isLogedin = sharedOTPCode.getLogInstate(SHAREDKEY , false)
        Log.d("islogin",isLogedin.toString())
        bar = ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(bar)
        bar.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (isLogedin== true){
            GlobalScope.launch {
                delay(200)
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.dialog_enter_anim,R.anim.dialog_exit_anim)
                    .add(R.id.container_dialog,CustomeDialog.newInstance("Logged in","",getColor(R.color.btn_color)),"CustomDialogues")
                    .addToBackStack("CustomDialogues")
                    .commit()
                delay(2000)
                supportFragmentManager.popBackStack("CustomDialogues",FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }
        nav_view.setNavigationItemSelectedListener {
            if (isLogedin == false){
                val intent = Intent(this,LoginvsSigUpActivity::class.java)
                startActivity(intent)
            }else{
                when (it.itemId) {
                    R.id.sidenav_menu -> {}
                    R.id.sidenav_loyalty -> {}
                    R.id.sidenav_oderhistory -> {}
                    R.id.sidenav_refer -> {}
                    R.id.sidenav_shopinfo -> {}
                    R.id.sidenav_notification -> {}
                    R.id.sidenav_logout->{
                        isLogedin = false
                        sharedOTPCode.saveLogInState(SHAREDKEY,isLogedin)
                        finish()
                        startActivity(intent)
                    }
                }
            }
            true
        }


        btn_side_menu.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

        nav_view.setOnClickListener {

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }


}