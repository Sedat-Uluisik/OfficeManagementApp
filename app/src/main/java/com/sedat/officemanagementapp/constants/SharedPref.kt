package com.sedat.officemanagementapp.constants

import android.content.SharedPreferences
import com.sedat.officemanagementapp.core.model.UserLogin

class SharedPref (private val sharedPreferences: SharedPreferences) {
    fun saveUserNameAndPassword(userLogin: UserLogin){
        with(sharedPreferences.edit()){
            putString("officemanagementapp_username", userLogin.username)
            putString("officemanagementapp_password", userLogin.password)
            putInt("officemanagementapp_status", userLogin.status)
            putInt("officemanagementapp_department", userLogin.departmentId)
            apply()
        }
    }

    fun getUserNameAndPassword(): UserLogin {
        with(sharedPreferences){
            val username = getString("officemanagementapp_username", " ").toString()
            val password = getString("officemanagementapp_password", " ").toString()
            val status = getInt("officemanagementapp_status", -1)
            val departmentId = getInt("officemanagementapp_department", -1)

            return UserLogin(username,password, status, departmentId)
        }
    }

    fun deleteUser(){
        with(sharedPreferences.edit()){
            remove("officemanagementapp_username")
            remove("officemanagementapp_password")
            remove("officemanagementapp_status")
            remove("officemanagementapp_department")
            apply()
        }
    }
}