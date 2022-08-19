package com.sedat.officemanagementapp.viewmodel.admin

import androidx.lifecycle.*
import com.sedat.officemanagementapp.constants.ShowToast
import com.sedat.officemanagementapp.core.model.Access
import com.sedat.officemanagementapp.core.model.Department
import com.sedat.officemanagementapp.core.model.Work
import com.sedat.officemanagementapp.core.model.WorkId
import com.sedat.officemanagementapp.repo.Repository
import com.sedat.officemanagementapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DepartmentViewModel @Inject constructor(
    private val repository: Repository,
    private val showToast: ShowToast
): ViewModel() {

    init {
        getAllDepartments()
    }

    //private var _departmentList = MutableLiveData<List<Department>>()
    //val departmentList: LiveData<List<Department>> get() = _departmentList

    /*fun getAllDepartments(){
        viewModelScope.launch {
            try {
                val department = repository.getAllDepartments()

                department.body()?.let { departmentList ->
                    _departmentList.value = departmentList
                }
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
    }*/

    private val _departmentState = MutableStateFlow(DepartmentsState())
    val departmentState = _departmentState
    fun getAllDepartments(){

        viewModelScope.launch {
            when(val response = repository.getAllDepartments2()){
                is Resource.Error ->
                    _departmentState.value =
                        DepartmentsState(null, response.message?.message, null)
                is Resource.Loading ->
                    _departmentState.value = DepartmentsState(true, "", null)
                is Resource.Success ->
                    _departmentState.value = DepartmentsState(null, "", response.data)
            }
        }
    }

    fun insertDepartment(departmentName: String): LiveData<Response<Department>>{
        val state = MutableLiveData<Response<Department>>()
        viewModelScope.launch {
            try {
                val department = Department(0, departmentName)

                val post = repository.insertDepartment(department)

                state.value = post
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
        return state
    }

    fun deleteDepartment(department: Department): LiveData<Response<Department>>{
        val state = MutableLiveData<Response<Department>>()
        viewModelScope.launch {
            try {
                val response = repository.deleteDepartment(department)

                state.value = response
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
        return state
    }

    fun updateDepartment(department: Department){
        viewModelScope.launch {
            try {
                val response = repository.updateDepartment(department)

                if(response.body() != null && response.isSuccessful){
                    _departmentState.value = DepartmentsState(null, null, response.body())
                }
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
    }

    fun insertDepartmentAccess(departmentId: Int, list: List<Access>): LiveData<Response<List<Access>>>{
        val code = MutableLiveData<Response<List<Access>>>()
        viewModelScope.launch {
            try {
                val result = repository.insertDepartmentAccess(departmentId, list)

                code.value = result
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
        return code
    }

    fun insertWorkForDepartment(departmentId: Int, list: List<WorkId>): LiveData<Int>{
        val code = MutableLiveData<Int>()
        viewModelScope.launch {
            try {
                val result = repository.insertWorkForDepartment(departmentId, list)
                code.value = result.code()
            }catch (ex: Exception){
                showToast.showErrorConnect()
            }
        }
        return code
    }

    fun getAllWorks(): LiveData<List<Work>>{
        val list = MutableLiveData<List<Work>>()
        viewModelScope.launch {
            when(val response = repository.getAllWorks()){
                is Resource.Loading -> {showToast.show("Tekrar deneyiniz")}
                is Resource.Error -> {showToast.show(response.message?.message.toString())}
                is Resource.Success -> list.value = response.data
            }
        }
        return list
    }
}

data class DepartmentsState(
    val isLoading: Boolean ?= null,
    val error: String ?= null,
    val data: List<Department> ?= null
)