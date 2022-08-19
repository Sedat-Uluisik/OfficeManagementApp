package com.sedat.officemanagementapp.utils

import retrofit2.Response

suspend fun <T> getResourceByNetworkRequest(request: suspend () -> Response<T>): Resource<T> {
    try {
        val response = request()
        if (response.isSuccessful) {
            response.body()?.apply {
                return Resource.Success(this)
            }
        }else if (response.code() == 404)
            return Resource.Error(Exception("Not Found!!"))
    } catch (e: Exception) {
        e.printStackTrace()
        return Resource.Error(e)
    }
    return Resource.Loading()
}