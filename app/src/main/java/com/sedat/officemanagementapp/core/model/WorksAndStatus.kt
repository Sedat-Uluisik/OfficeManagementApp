package com.sedat.officemanagementapp.core.model


import com.google.gson.annotations.SerializedName

data class WorksAndStatus(
    @SerializedName("createdDate")
    val createdDate: String?,
    @SerializedName("departmentName")
    val departmentName: String?,
    @SerializedName("idDepartment")
    val idDepartment: Int?,
    @SerializedName("idWork")
    val idWork: Int?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("updatedDate")
    val updatedDate: String?,
    @SerializedName("workName")
    val workName: String?
)