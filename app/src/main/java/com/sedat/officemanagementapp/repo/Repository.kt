package com.sedat.officemanagementapp.repo

import com.sedat.officemanagementapp.core.model.*
import com.sedat.officemanagementapp.network.service.Api
import com.sedat.officemanagementapp.utils.Resource
import com.sedat.officemanagementapp.utils.getResourceByNetworkRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: Api
) {
    suspend fun getUserWithNameAndPassword(username: String, password: String): Resource<User> {
        return getResourceByNetworkRequest {
            api.getUserWithNameAndPassword(username, password)
        }
    }

    suspend fun getAllWorks() : Resource<List<Work>>{
        return getResourceByNetworkRequest { api.getAllWorks() }
    }

    suspend fun insertWork(work: Work): Response<Work> {
        return api.insertWork(work)
    }

    suspend fun deleteWork(work: Work): Response<Work>{
        return api.deleteWork(work)
    }

    suspend fun updateWork(work: Work): Response<List<Work>>{
        return api.updateWork(work)
    }

    suspend fun getAllUsers(): Resource<List<User>> {
        return getResourceByNetworkRequest { api.getAllUsers() }
    }

    suspend fun insertUser(user: User): Response<User> {
        return api.insertUser(user)
    }

    suspend fun deleteUser(username: String, password: String): Response<List<User>>{
        return api.deleteUser(username, password)
    }

    suspend fun updateUser(user: User): Response<List<User>>{
        return api.updateUser(user)
    }

    suspend fun getAllDepartments(): Response<List<Department>>{
        return api.getAllDepartments()
    }

    //for flow
    suspend fun getAllDepartments2(): Resource<List<Department>>{
        return getResourceByNetworkRequest {
            api.getAllDepartments2()
        }
    }

    suspend fun insertDepartment(department: Department): Response<Department> {
        return api.insertDepartment(department)
    }

    suspend fun deleteDepartment(department: Department): Response<Department>{
        return api.deleteDepartment(department)
    }

    suspend fun updateDepartment(department: Department): Response<List<Department>>{
        return api.updateDepartment(department)
    }

    suspend fun insertDepartmentAccess(departmentId: Int, list: List<Access>): Response<List<Access>>{
        return api.insertDepartmentAccess(departmentId, list)
    }

    suspend fun insertWorkForDepartment(departmentId: Int, list: List<WorkId>): Response<List<WorksAndStatus>>{
        return api.insertWorkForDepartment(departmentId, list)
    }

    suspend fun getAllWorkStatus(): Response<List<WorksAndStatus>>{
        return api.getAllWorkStatus()
    }

    suspend fun getWorkStatusWithUserDepartment(departmentId: Int): Response<List<WorksAndStatus>>{
        return api.getWorkStatusWithUserDepartment(departmentId)
    }

    suspend fun deleteDepartmentAndWork(departmentId: Int, workId: Int): Response<List<WorksAndStatus>>{
        return api.deleteDepartmentAndWork(departmentId, workId)
    }

    suspend fun updateWorkStatusToDone(
        departmentId: Int,
        workId: Int,
        workStatus: Int
    ): Response<List<WorksAndStatus>>{
        return api.updateWorkStatusToDone(departmentId, workId, workStatus)
    }

    suspend fun reportWork(
        departmentId: Int,
        workId: Int,
        workStatus: Int,
        userStatus: Int
    ): Response<List<WorksAndStatus>>{
        return api.reportWork(departmentId, workId, workStatus, userStatus)
    }
}