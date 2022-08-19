package com.sedat.officemanagementapp.core.model

import com.google.gson.annotations.SerializedName

data class Access(
    @SerializedName("departmentAccessId")
    val departmentAccessId: Int?
)