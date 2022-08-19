package com.sedat.officemanagementapp.core.model

data class User(
    val id: Int,
    val userName: String,
    val password: String,
    val departmentId: Int,
    val statusId: Int
)