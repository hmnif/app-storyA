package com.example.storyapp_awal.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp_awal.data.AppRepository
import com.example.storyapp_awal.data.Results
import com.example.storyapp_awal.data.remote.response.ListStoryItem

class HomeViewModel(repository: AppRepository) : ViewModel() {
    val getAllStories: LiveData<Results<List<ListStoryItem>>> = repository.getAllStories()
}