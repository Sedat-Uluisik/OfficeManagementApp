package com.sedat.officemanagementapp.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sedat.officemanagementapp.constants.ShowToast
import com.sedat.officemanagementapp.core.model.WorksAndStatus
import com.sedat.officemanagementapp.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorksStatesViewModel @Inject constructor(
    private val repository: Repository,
    private val showToast: ShowToast
): ViewModel() {

    private var _workStatusList = MutableLiveData<List<WorksAndStatus>>()
    val workStatusList: LiveData<List<WorksAndStatus>> get() = _workStatusList

    //for admin
    fun getAllWorkStatus(){
        viewModelScope.launch {
            try {
                val results = repository.getAllWorkStatus()

                if(results.body() != null){
                    if(results.body()!!.isNotEmpty())
                        _workStatusList.value = results.body()
                }else
                    showToast.show("Tekrar deneyiniz")
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
    }

    //for user
    fun getWorkStatusForDepartment(departmentId: Int){
        viewModelScope.launch {
            try {
                val workStatusList = repository.getWorkStatusWithUserDepartment(departmentId)

                if(workStatusList.code() == 200 && workStatusList.body() != null)
                    _workStatusList.value = workStatusList.body()
                else
                    showToast.show("Yüklerken bir sorun oluştu")
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
    }

    fun deleteDepartmentAndWork(departmentId: Int, workId: Int){
        viewModelScope.launch {
            try {
                val newList = repository.deleteDepartmentAndWork(departmentId, workId)

                if(newList.body() != null && newList.code() == 200)
                    _workStatusList.value = newList.body()
                else
                    showToast.show("Yüklerken bir sorun oluştu")
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
    }

    fun changeWorkStatus(userDepartmentId: Int, departmentId: Int, workId: Int, workStatus: Int, userStatus: Int){
        viewModelScope.launch {
            try {
                val newList = repository.reportWork(
                    departmentId, workId, workStatus, userStatus
                )

                /*
                userDepartmentId -> raporlama sonrası kendi departmanına ait verileri getirir
                departmentId -> raporlama için kullanılıyor.
                 */
                if(newList.code() == 200)
                    getWorkStatusForDepartment(userDepartmentId)
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
    }

    fun updateWorkStatusToDone(departmentId: Int, workId: Int, workStatus: Int){
        viewModelScope.launch {
            try {
                val newList = repository.updateWorkStatusToDone(
                    departmentId, workId, workStatus
                )

                _workStatusList.value = listOf()

                if(newList.body() != null && newList.code() == 200){
                    if(newList.body()!!.isNotEmpty())
                        _workStatusList.value = newList.body()
                }else
                    getWorkStatusForDepartment(departmentId)
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
    }
}