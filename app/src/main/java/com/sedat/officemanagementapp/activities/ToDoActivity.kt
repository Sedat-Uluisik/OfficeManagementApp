package com.sedat.officemanagementapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.onesignal.OneSignal
import com.sedat.officemanagementapp.constants.PopupMenu
import com.sedat.officemanagementapp.constants.SharedPref
import com.sedat.officemanagementapp.core.model.*
import com.sedat.officemanagementapp.databinding.ActivityToDoBinding
import com.sedat.officemanagementapp.listener.PopupMenuItemClickListener
import com.sedat.officemanagementapp.utils.Constants
import com.sedat.officemanagementapp.utils.Constants.package_name
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ToDoActivity : AppCompatActivity(), PopupMenuItemClickListener {

    private val binding: ActivityToDoBinding by lazy { ActivityToDoBinding.inflate(layoutInflater) }
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        sharedPreferences = this.getSharedPreferences(package_name, Context.MODE_PRIVATE)

        binding.btnMenu.setOnClickListener{
            PopupMenu().showPopupMenuForLogOut(
                this,
                it,
                this
            )
        }
    }

    override fun deleteWithPopup(department: Department?, work: Work?, user: User?) {
        TODO("Not yet implemented")
    }

    override fun updateWithPopup(department: Department?, work: Work?, user: User?) {
        TODO("Not yet implemented")
    }

    override fun insertAccess(department: Department?) {
        TODO("Not yet implemented")
    }

    override fun insertWorkToDepartment(department: Department?) {
        TODO("Not yet implemented")
    }

    override fun logOutBtnClick() {
        SharedPref(sharedPreferences).saveUserNameAndPassword(
            UserLogin(" ", " ", -1, -1)
        )

        unsubscribeOneSignal()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun reportBtnClick(worksAndStatus: WorksAndStatus) {
        TODO("Not yet implemented")
    }

    override fun deleteDepartmentAndWorkBtnClick(worksAndStatus: WorksAndStatus) {
        TODO("Not yet implemented")
    }

    override fun doneBtnClick(worksAndStatus: WorksAndStatus) {
        TODO("Not yet implemented")
    }

    private fun unsubscribeOneSignal(){

        OneSignal.deleteTag("notification_topic")
        OneSignal.deleteTag("department")
    }
}