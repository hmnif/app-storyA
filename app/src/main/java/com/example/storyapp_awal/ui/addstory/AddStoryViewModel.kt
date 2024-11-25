package com.example.storyapp_awal.ui.addstory

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp_awal.data.AppRepository
import com.example.storyapp_awal.data.Results
import com.example.storyapp_awal.data.remote.response.StoryUploadResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val repository: AppRepository): ViewModel() {
    private val _imageUri = MutableLiveData<Uri?>()
    val imageUri: LiveData<Uri?> = _imageUri

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _uploadResult = MutableLiveData<Results<StoryUploadResponse>>()
    val uploadResult: LiveData<Results<StoryUploadResponse>> = _uploadResult


    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }

    fun getImageUri(): Uri? {
        return _imageUri.value
    }

    fun uploadStory(
        description: RequestBody,
        photo: MultipartBody.Part,
        lat: RequestBody?,
        lon: RequestBody?
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.uploadStory(description, photo, lat, lon)
            _uploadResult.value = result
            _isLoading.value = false
        }
    }
}