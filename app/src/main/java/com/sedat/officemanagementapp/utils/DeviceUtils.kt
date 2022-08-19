package com.sedat.officemanagementapp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class DeviceUtils {
    companion object {
        fun openKeyboard(activity: Activity, editText: EditText?) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }

        fun closeKeyboard(view: View) {
            view.setOnFocusChangeListener { _view, bool ->
                if (!bool) {
                    val imm =
                        view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(_view.windowToken, 0)
                }
            }
        }

        /*fun closeKeyboard(activity: Activity?) {
            activity?.let {
                val inputMethodManager =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
            }
        }*/
    }
}