package com.sedat.officemanagementapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.sedat.officemanagementapp.R
import com.sedat.officemanagementapp.constants.PopupMenu
import com.sedat.officemanagementapp.constants.SharedPref
import com.sedat.officemanagementapp.core.model.*
import com.sedat.officemanagementapp.databinding.ActivityAdminPanelBinding
import com.sedat.officemanagementapp.listener.PopupMenuItemClickListener
import com.sedat.officemanagementapp.utils.Constants.package_name
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminPanelActivity : AppCompatActivity(), PopupMenuItemClickListener {

    private val binding: ActivityAdminPanelBinding by lazy { ActivityAdminPanelBinding.inflate(layoutInflater) }

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        sharedPreferences = this.getSharedPreferences(package_name, Context.MODE_PRIVATE)

        val navController = findNavController(R.id.fragment_container_view)
        setUpBottomNavView(navController)

        binding.btnMenu.setOnClickListener {
            PopupMenu().showPopupMenuForLogOut(
                this,
                it,
                this
            )
        }

    }

    private fun setUpBottomNavView(navController: NavController){
        binding.bottomMenu.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.fragment_container_view)) || super.onOptionsItemSelected(item)
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

}