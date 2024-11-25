package com.example.storyapp_awal.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp_awal.data.AppRepository
import com.example.storyapp_awal.data.Results
import com.example.storyapp_awal.data.remote.response.LoginResult
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AppRepository) : ViewModel() {

    fun login(email: String, password: String, onSuccess: (LoginResult) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            when (val results = repository.login(email, password)) {
                is Results.Loading -> {
                    // Do nothing
                }
                is Results.Success -> {
                    val loginResult = results.data.loginResult
                    if (loginResult != null) {
                        repository.saveToken(loginResult.token ?: "")
                        onSuccess(loginResult)
                    } else {
                        onError("Login failed: no results")
                    }
                }
                is Results.Error -> {
                    onError(results.error)
                }
            }
        }
    }
}