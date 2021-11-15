package com.example.evergreen.View.Base

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    open fun onBackPress() {
        if (activity != null) {
            (activity as BaseActivity).backRoot()
        }
    }
}