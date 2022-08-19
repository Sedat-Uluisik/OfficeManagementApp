package com.sedat.officemanagementapp.network.service

import com.sedat.officemanagementapp.core.model.*
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @GET("getUserWithUserNameAndPassword/{userName}/{password}")
    suspend fun getUserWithNameAndPassword(
        @Path("userName") userName: String,
        @Path("password") password: String
    ): Response<User>

    @GET("allUsers")
    suspend fun getAllUsers(): Response<List<User>>

    @Headers("Content-Type: application/json")
    @POST("insertUser")
    suspend fun insertUser(
        @Body user: User
    ): Response<User>

    @DELETE("deleteUser/{username}/{password}")
    suspend fun deleteUser(
        @Path("username") username: String,
        @Path("password") password: String
    ): Response<List<User>>

    @Headers("Content-Type: application/json")
    @HTTP(method = "PUT", path = "updateUser", hasBody = true)
    suspend fun updateUser(
        @Body user: User
    ): Response<List<User>>

    @GET("getAllDepartments")
    suspend fun getAllDepartments(
    ): Response<List<Department>>

    @GET("getAllDepartments")
    suspend fun getAllDepartments2( //flow demo
    ): Response<List<Department>>

    @Headers("Content-Type: application/json")
    @POST("insertDepartment")
    suspend fun insertDepartment(
        @Body department: Department
    ): Response<Department>

    @Headers("Content-Type: application/json")
    //body kullanılarak silme yapıldığı için bu yöntem kullanıldı
    @HTTP( method = "DELETE", path = "deleteDepartment", hasBody = true)
    suspend fun deleteDepartment(
        @Body department: Department
    ): Response<Department>

    @Headers("Content-Type: application/json")
    //body kullanılarak silme yapıldığı için bu yöntem kullanıldı
    @HTTP( method = "PUT", path = "updateDepartment", hasBody = true)
    suspend fun updateDepartment(
        @Body department: Department
    ): Response<List<Department>>

    @GET("allWorks")
    suspend fun getAllWorks(
    ): Response<List<Work>>

    @Headers("Content-Type: application/json")
    @POST("insertWork")
    suspend fun insertWork(
        @Body work: Work
    ): Response<Work>

    @Headers("Content-Type: application/json")
    //body kullanılarak silme yapıldığı için bu yöntem kullanıldı
    //body olmasaydı @DELETE kullanılacaktı
    @HTTP( method = "PUT", path = "updateWork", hasBody = true)
    suspend fun updateWork(
        @Body work: Work
    ): Response<List<Work>>

    @Headers("Content-Type: application/json")
    //body kullanılarak silme yapıldığı için bu yöntem kullanıldı
    @HTTP( method = "DELETE", path = "deleteWork", hasBody = true)
    suspend fun deleteWork(
        @Body work: Work
    ): Response<Work>

    @Headers("Content-Type: application/json")
    @POST("insertDepartmentAccessList/{departmentId}")
    suspend fun insertDepartmentAccess(
        @Path("departmentId") departmentId: Int,
        @Body list: List<Access>
    ): Response<List<Access>>

    @Headers("Content-Type: application/json")
    @POST("insertWorkForDepartment/{departmentId}")
    suspend fun insertWorkForDepartment(
        @Path("departmentId") departmentId: Int,
        @Body workIdList: List<WorkId>
    ): Response<List<WorksAndStatus>>

    @GET("getAllWorkStatus")
    suspend fun getAllWorkStatus(

    ): Response<List<WorksAndStatus>>

    @GET("getWorkStatusForDepartment/{departmentId}")
    suspend fun getWorkStatusWithUserDepartment(
        @Path("departmentId") departmentId: Int
    ): Response<List<WorksAndStatus>>

    @DELETE("deleteDepartmentAndWork/{departmentId}/{workId}")
    suspend fun deleteDepartmentAndWork(
        @Path("departmentId") departmentId: Int,
        @Path("workId") workId: Int
    ): Response<List<WorksAndStatus>>

    @PUT("changeWorkStatusToDone/{departmentId}/{workId}/{workStatus}")
    suspend fun updateWorkStatusToDone(
        @Path("departmentId") departmentId: Int,
        @Path("workId") workId: Int,
        @Path("workStatus") workStatus: Int
    ): Response<List<WorksAndStatus>>

    @PUT("changeWorkStatus/{departmentId}/{workId}/{workStatus}/{userStatus}")
    suspend fun reportWork(
        @Path("departmentId") departmentId: Int,
        @Path("workId") workId: Int,
        @Path("workStatus") workStatus: Int,
        @Path("userStatus") userStatus: Int
    ): Response<List<WorksAndStatus>>

}