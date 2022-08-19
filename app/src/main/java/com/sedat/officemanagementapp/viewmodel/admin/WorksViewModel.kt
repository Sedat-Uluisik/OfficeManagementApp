package com.sedat.officemanagementapp.viewmodel.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sedat.officemanagementapp.constants.ShowToast
import com.sedat.officemanagementapp.core.model.Work
import com.sedat.officemanagementapp.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WorksViewModel @Inject constructor(
    private val repository: Repository,
    private val showToast: ShowToast
): ViewModel() {

    init {
        getAllWork()
    }

    private var _workList = MutableLiveData<List<Work>>()
    val workList: LiveData<List<Work>> get()=_workList

    fun getAllWork(){
        viewModelScope.launch {
            try {
                val works = repository.getAllWorks()

                if (works.code()== 200)
                    _workList.value = works.body()
                else
                    showToast.show("Tekrar deneyiniz")
            }catch (ex: Exception){
                showToast.showErrorConnect()
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