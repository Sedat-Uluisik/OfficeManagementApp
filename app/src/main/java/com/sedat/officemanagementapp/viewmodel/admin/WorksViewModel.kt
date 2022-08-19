package com.sedat.officemanagementapp.viewmodel.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sedat.officemanagementapp.constants.ShowToast
import com.sedat.officemanagementapp.core.model.Work
import com.sedat.officemanagementapp.repo.Repository
import com.sedat.officemanagementapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WorksViewModel @Inject constructor(
    private val repository: Repository,
    private val showToast: ShowToast
): ViewModel() {

    private var _workList = MutableLiveData<List<Work>>()
    val workList: LiveData<List<Work>> get()=_workList
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get()= _isLoading

    init {
        getAllWork()
    }

    fun getAllWork(){
        _isLoading.value = true
        viewModelScope.launch {
            when(val response = repository.getAllWorks()){
                is Resource.Loading -> _isLoading.value = true
                is Resource.Error -> {
                    showToast.show(response.message?.message.toString())
                    _isLoading.value = false
                }
                is Resource.Success -> {
                    _workList.value = response.data
                    _isLoading.value = false
                }
            }
        }
    }

    fun insertWork(workName: String): LiveData<Response<Work>>{
        val state = MutableLiveData<Response<Work>>()
        viewModelScope.launch {
            try {
                val work = Work(0, workName)

                val postData = repository.insertWork(work)

                state.value = postData
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
        return state
    }

    fun deleteWork(work: Work): LiveData<Response<Work>>{
        val state = MutableLiveData<Response<Work>>()
        viewModelScope.launch {
            try {
                val response = repository.deleteWork(work)

                state.value = response
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
        return state
    }

    fun updateWork(work: Work){
        viewModelScope.launch {
            try {
                val response = repository.updateWork(work)

                if(response.isSuccessful && response.body() != null)
                    _workList.value = response.body()
                else
                    showToast.show("Tekrar deneyiniz")
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
    }
}