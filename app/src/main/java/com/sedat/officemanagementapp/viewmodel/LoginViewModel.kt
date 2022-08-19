package com.sedat.officemanagementapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sedat.officemanagementapp.constants.ShowToast
import com.sedat.officemanagementapp.core.model.User
import com.sedat.officemanagementapp.repo.Repository
import com.sedat.officemanagementapp.utils.Resource
import com.sedat.officemanagementapp.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    private val showToast: ShowToast
): ViewModel() {

    private var _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user
    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun getUserWithNameAndPassword(username: String, password: String){
        _loading.value = true
        viewModelScope.launch {
            when(val response = repository.getUserWithNameAndPassword(username, password)){
                is Resource.Error -> {
                    showToast.show(response.message?.message.toString())
                    _loading.value = false
                }
                is Resource.Loading -> _loading.value = true
                is Resource.Success -> {
                    _user.value = response.data
                    _loading.value = false
                }
            }
        }
    }
}