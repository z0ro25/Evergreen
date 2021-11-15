package com.example.evergreen.custumview

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.evergreen.R
import kotlinx.android.synthetic.main.dialog_layout_erro.*
import kotlinx.android.synthetic.main.phone_verify_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CustomeDialog : DialogFragment() {
    private var title = ""
    private var mess = ""
    private var color = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_layout_erro,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            title = it.getString(TITLEKEY).toString()
            mess = it.getString(MESSKEY).toString()
            color = it.getInt(COLORKEY)
        }
        dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        dialog?.window?.attributes?.windowAnimations = R.style.NoMarginsDialog
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
        tv_title.text = title
        tv_mess.text = mess
        dialog_background.setBackgroundColor(color)
    }

    companion object {
        private val TITLEKEY: String = "TITLEKEY"
        private val MESSKEY: String = "MESSKEY"
        private val COLORKEY: String = "COLORKEY"

        fun newInstance(title : String, mess: String,color: Int) = CustomeDialog().apply {
            arguments = Bundle().apply {
                putString(TITLEKEY,title)
                putString(MESSKEY,mess)
                putInt(COLORKEY,color)
            }
        }
    }
}
