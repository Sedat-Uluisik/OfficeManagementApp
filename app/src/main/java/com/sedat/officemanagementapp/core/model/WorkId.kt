package com.sedat.officemanagementapp.core.model

import com.google.gson.annotations.SerializedName

//back-end e workId listesi göndermek için kullanılıyor.
data class WorkId(
    @SerializedName("idWork")
    val workId: Int?
)