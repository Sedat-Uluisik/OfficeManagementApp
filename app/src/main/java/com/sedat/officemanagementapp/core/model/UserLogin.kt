package com.sedat.officemanagementapp.core.model

data class UserLogin(
    var username: String,
    var password: String,
    var status: Int,
    var departmentId: Int
)
