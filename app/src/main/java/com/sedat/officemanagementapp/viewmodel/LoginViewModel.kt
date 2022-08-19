package com.sedat.officemanagementapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sedat.officemanagementapp.constants.ShowToast
import com.sedat.officemanagementapp.core.model.User
import com.sedat.officemanagementapp.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    private val showToast: ShowToast
): ViewModel() {

    fun getUserWithNameAndPassword(username: String, password: String): LiveData<Response<User>>{
        val status = MutableLiveData<Response<User>>()
        viewModelScope.launch {
            try {
                val user = repository.getUserWithNameAndPassword(username, password)

                status.value = user
            }catch (ex: Exception){
                showToast.show("Bağlantı hatası")
            }
        }
        return status
    }
}