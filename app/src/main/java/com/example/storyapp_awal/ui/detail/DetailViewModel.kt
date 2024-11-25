package com.example.storyapp_awal.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp_awal.data.AppRepository
import com.example.storyapp_awal.data.Results
import com.example.storyapp_awal.data.remote.response.DetailResponse
import com.example.storyapp_awal.data.remote.response.Story

class DetailViewModel(private val repository: AppRepository) : ViewModel() {

    private val _story = MutableLiveData<DetailResponse>()
    val story: LiveData<DetailResponse> = _story

    fun getDetailStory(storyId: String): LiveData<Results<Story>> {
        return repository.getDetailStory(storyId)
    }
}