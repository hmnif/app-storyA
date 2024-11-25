package com.example.storyapp_awal.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp_awal.data.AppRepository
import com.example.storyapp_awal.data.Results
import com.example.storyapp_awal.data.remote.response.RegisterResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AppRepository) : ViewModel() {

    private val _registerResult = MutableStateFlow<Results<RegisterResponse>>(Results.Loading)
    val registerResult: StateFlow<Results<RegisterResponse>> = _registerResult

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _registerResult.value = repository.register(name, email, password)
        }
    }
}