package com.sedat.officemanagementapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.onesignal.OneSignal
import com.onesignal.OneSignal.sendTag
import com.sedat.officemanagementapp.constants.SharedPref
import com.sedat.officemanagementapp.constants.ShowToast
import com.sedat.officemanagementapp.databinding.ActivityLoginBinding
import com.sedat.officemanagementapp.core.model.UserLogin
import com.sedat.officemanagementapp.utils.Constants.package_name
import com.sedat.officemanagementapp.utils.DeviceUtils.Companion.closeKeyboard
import com.sedat.officemanagementapp.utils.DeviceUtils.Companion.openKeyboard
import com.sedat.officemanagementapp.utils.gone
import com.sedat.officemanagementapp.utils.visible
import com.sedat.officemanagementapp.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var showToast: ShowToast
    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    private val viewModel: LoginViewModel by viewModels()
    lateinit var sharedPreferences: SharedPreferences

    private val ONESIGNAL_APP_ID = "da2ae273-1399-42b7-96e4-03567d24795f"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        sharedPreferences = this.getSharedPreferences(package_name, Context.MODE_PRIVATE)

        autoLogin()

        binding.loginButton.setOnClickListener {

            val username = binding.userName.text.toString()
            val password = binding.password.text.toString()

            if(username.isNotEmpty() && password.isNotEmpty())
                login(username, password)
            else{
                showToast.show("Kullanıcı adı veya şifre boş olamaz!")
            }
        }

        binding.userName.requestFocus()
        openKeyboard(this, binding.userName)
        //listener
        closeKeyboard(binding.userName)
        closeKeyboard(binding.password)
    }

    private fun autoLogin(){
        val user = SharedPref(sharedPreferences).getUserNameAndPassword()
        if(user.username.isNotEmpty() && user.password.isNotEmpty() && user.status != -1){
            if(user.status == 1 || user.status == 2){
                val intent = Intent(this, ToDoActivity::class.java)
                startActivity(intent)
                finish()
            }
            else if(user.status == 3){
                val intent = Intent(this, AdminPanelActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun login(userName: String, password: String){
        viewModel.getUserWithNameAndPassword(userName, password)
        viewModel.loading.observe(this){
            if(it)
                binding.progressBarLogin.visible()
            else
                binding.progressBarLogin.gone()
        }
        viewModel.user.observe(this){

            if(it.statusId == 1 || it.statusId == 2){

                SharedPref(sharedPreferences).saveUserNameAndPassword(
                    UserLogin(
                        userName,
                        password,
                        it.statusId,
                        it.departmentId)
                )

                if(it.statusId == 2)
                    subscribeToOneSignal("Hizmetli", it.departmentId)
                else
                    subscribeToOneSignal("Kullanıcı", it.departmentId)

                val intent = Intent(this, ToDoActivity::class.java)
                startActivity(intent)
                finish()

            }else if(it.statusId == 3){

                SharedPref(sharedPreferences).saveUserNameAndPassword(
                    UserLogin(userName, password, 3, it.departmentId)
                )

                val intent = Intent(this, AdminPanelActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun subscribeToOneSignal(segment: String, departmentId: Int){
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

        sendTag("notification_topic", segment)
        sendTag("department", departmentId.toString())
    }
}