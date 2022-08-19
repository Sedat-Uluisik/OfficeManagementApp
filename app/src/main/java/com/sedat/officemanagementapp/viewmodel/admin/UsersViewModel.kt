package com.sedat.officemanagementapp.viewmodel.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sedat.officemanagementapp.constants.ShowToast
import com.sedat.officemanagementapp.core.model.Department
import com.sedat.officemanagementapp.core.model.User
import com.sedat.officemanagementapp.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: Repository,
    private val showToast: ShowToast
): ViewModel() {

    init {
        getAllUsers()
    }

    private var _userList = MutableLiveData<List<User>>()
    val workList: LiveData<List<User>> get()=_userList

    fun getAllUsers(){
        viewModelScope.launch {
            try {
                val users = repository.getAllUsers()

                if(users != null && users.isNotEmpty())
                    _userList.value = users
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
    }

    fun getAllDepartment(): LiveData<List<Department>>{
        val list = MutableLiveData<List<Department>>()
        viewModelScope.launch {
            try {
                val departments = repository.getAllDepartments()

                if(departments.body() != null){
                    if(departments.body()!!.isNotEmpty()){
                        list.value = departments.body()
                    }
                }
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
        return list
    }

    fun insertUser(user: User){
        viewModelScope.launch {
            try {
                val insertUser = repository.insertUser(user)

                if(insertUser.isSuccessful && insertUser.code() == 200)
                    getAllUsers()
                else
                    showToast.show("Tekrar deneyiniz")
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
    }

    fun updateUser(user: User){
        viewModelScope.launch {
            try {
                val updateUser = repository.updateUser(user)

                if(updateUser.isSuccessful && updateUser.body() != null)
                    _userList.value = updateUser.body()
                else
                    showToast.show("Tekrar deneyiniz")
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
    }

    fun deleteUser(user: User){
        viewModelScope.launch {
            try {
                val result = repository.deleteUser(user.userName, user.password)

                if(result.isSuccessful && result.body() != null)
                    _userList.value = result.body()
                else
                    showToast.show("Tekrar deneyiniz")
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
    }
}