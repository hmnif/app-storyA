package com.example.storyapp_awal.di

import android.content.Context
import com.example.storyapp_awal.data.AppRepository
import com.example.storyapp_awal.data.pref.UserPref
import com.example.storyapp_awal.data.remote.retrofit.ApiConfig
import com.example.storyapp_awal.dataStore

object Injection {
    fun provideAppRepository(context: Context): AppRepository {
        val apiService = ApiConfig.getApiService()
        val pref = UserPref.getInstance(context.dataStore)
        return AppRepository.getInstance(apiService, pref)
    }
}