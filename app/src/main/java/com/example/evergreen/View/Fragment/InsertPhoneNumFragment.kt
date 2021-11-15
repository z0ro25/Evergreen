package com.example.evergreen.View.Fragment

import android.annotation.SuppressLint
import android.icu.number.IntegerWidth
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IntegerRes
import com.example.evergreen.R
import com.example.evergreen.View.Base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.phonenumber_insert_layout.*
@AndroidEntryPoint
class InsertPhoneNumFragment : BaseFragment() {
    private val PARSER_KEY = "PARSER_KEY"
    private var otpCode = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.phonenumber_insert_layout, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        VerifyOTPCodeFragment()
        arguments?.let {
            otpCode = it.getInt(PARSER_KEY)
            Log.d("otpCode",otpCode.toString())
        }
        edt_phonenum.inputType = InputType.TYPE_CLASS_NUMBER
        btn_next.isEnabled = false
        edt_phonenum.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                btn_next.isEnabled = true
            }

        })
        btn_next.setOnClickListener {
            if (edt_phonenum.text.isNullOrEmpty() || edt_phonenum.text.length > 11 || edt_phonenum.text.length < 10) {
                Toast.makeText(requireContext().applicationContext,
                    "your phone number is invalidate",
                    Toast.LENGTH_LONG).show()
            } else {
                val userPhoneNumber = Integer.parseInt(edt_phonenum.text.toString())
                Log.d("userPhoneNumber",userPhoneNumber.toString())
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fragment_enter_anim,R.anim.fragment_exit_anim)
                    .replace(R.id.frm_container, VerifyOTPCodeFragment.newInstance(userPhoneNumber,otpCode), "verifyOTPCodeFragment")
                    .addToBackStack("verifyOTPCodeFragment")
                    .commit()
            }
        }
    }
    companion object{
        @JvmStatic
        fun newInstance(code : Int ) =
            InsertPhoneNumFragment().apply {
                arguments = Bundle().apply {
                    putInt(PARSER_KEY,code)
                }
            }
    }
}