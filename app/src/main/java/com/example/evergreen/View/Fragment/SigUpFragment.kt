package com.example.evergreen.View.Fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.evergreen.Model.Data.User
import com.example.evergreen.R
import com.example.evergreen.View.Activity.HomeScreenActivity
import com.example.evergreen.View.Base.BaseFragment
import com.example.evergreen.View.sharedpreference.SharedOTPCode
import com.example.evergreen.ViewModel.SigUpViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.sig_up_layout.*
import java.text.SimpleDateFormat
import java.util.*
@AndroidEntryPoint
class SigUpFragment : BaseFragment() {
    private val PASRT_KEY: String = "PASRT_KEY"
    private var listlocalUser: List<User> = listOf()
    lateinit var userAdded: User
    private val userBrithDay = Calendar.getInstance()
    var currenDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    var currenMonth = Calendar.getInstance().get(Calendar.MONTH)
    var currenYear = Calendar.getInstance().get(Calendar.YEAR)
    private var userPhoneNumber = 0
    private val isloggedin = true
    lateinit var sigUpViewmodel : SigUpViewmodel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.sig_up_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sigup_user_name.requestFocus()
        arguments?.let {
            userPhoneNumber = it.getInt(PASRT_KEY)
        }
        sigup_date.setOnClickListener {
            DatePickerDialog(requireContext(),
                DatePickerDialog.OnDateSetListener { datePicker, YY, MM, DD ->
                    var time = "$DD/${MM + 1}/$YY"
                    sigup_date.setText(time)
                    userBrithDay.set(YY, MM, DD)
                },
                currenYear,
                currenMonth,
                currenDay).show()
        }
        btn_done_sigup.setOnClickListener {
            val userName = sigup_user_name.text.toString()
            val email = sigup_email.text.toString()
            val phoneNumber = userPhoneNumber
            val dateOfBirth = sigup_date.text.toString().trim()
            val password = sigup_password.text.toString()
            userAdded = User(null, userName, dateOfBirth, email, phoneNumber, password)
            Log.d("user", userAdded.toString())
            CheckImFormation()
        }
    }

    private fun CheckImFormation() {

        sigUpViewmodel = ViewModelProvider(this).get(SigUpViewmodel::class.java)
        sigUpViewmodel.getAllDataUserObservers().observe(viewLifecycleOwner, Observer {
            listlocalUser = it
        })
        if (listlocalUser.isEmpty()) {
            checkEmail()
        } else {
            checkEmail()
        }

    }

    private fun checkEmail() {
        if (sigup_user_name.text!!.length >= 20 || sigup_user_name.text!!.length <= 10) {
            username_tip_layout.helperText = "First and Last Name Required"
        } else username_tip_layout.helperText = ""
        if (userAdded.email?.contains("@gmail.com") == false) {
            email_tip_layout.helperText = "Invalid Email Address"
        } else email_tip_layout.helperText = ""
        if (checkAge(userBrithDay)) {
            //login
            saveNewUser(userAdded)
        } else showDiaLog()


    }
        private fun checkAge(mdate: Calendar): Boolean {
            var userAge = 0
            if (sigup_date.text.isEmpty()) return false
            if (currenYear - 21 > mdate.get(Calendar.YEAR) ) return true
            if  (currenYear - 21 != mdate.get(Calendar.YEAR) ) return false
            if (currenMonth < mdate.get(Calendar.MONTH)  ) return false
            if (currenMonth != mdate.get(Calendar.MONTH) ) return true
            userAge = (if ( currenDay < mdate.get(Calendar.DAY_OF_MONTH)) {
                currenYear - (mdate.get(Calendar.YEAR) + 1)
            } else currenYear - (mdate.get(Calendar.YEAR)))
            return userAge >= 21
        }


    private fun showDiaLog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Under 21+")
            .setCancelable(false)
            .setMessage("MassRoot requires users to be 21+ to comply with rules and regulations")
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            .create()
            .show()
    }

    private fun saveNewUser(user: User) {
        sigUpViewmodel = ViewModelProvider(this).get(SigUpViewmodel::class.java)
        sigUpViewmodel.addNewUser(user)
        Toast.makeText(requireContext().applicationContext, "welcome", Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext().applicationContext, HomeScreenActivity::class.java)
        saveToShared(LogInPasswordFragment.SHAREDKEY,isloggedin)
        startActivity(intent)
    }

    private fun saveToShared(Key : String,value: Boolean) {
        val sharedOTPCode = SharedOTPCode(requireActivity())
        sharedOTPCode.saveLogInState(Key,value)
    }
    companion object {
        @JvmStatic
        fun newInstance(number: Int) =
            SigUpFragment().apply {
                arguments = Bundle().apply {
                    putInt(PASRT_KEY, number)
                }
            }
    }
}