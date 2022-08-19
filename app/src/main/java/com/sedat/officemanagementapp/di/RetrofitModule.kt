package com.sedat.officemanagementapp.di

import android.content.Context
import com.sedat.officemanagementapp.network.service.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun initRetrofit(): Api {
        return Retrofit.Builder()
            .baseUrl("http://10.30.30.14:1987/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun ShowToast(@ApplicationContext context: Context) = com.sedat.officemanagementapp.constants.ShowToast(context)
}