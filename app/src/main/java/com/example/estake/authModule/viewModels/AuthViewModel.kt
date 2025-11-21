package com.example.estake.authModule.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.estake.authModule.repositories.AuthRepository
import com.example.estake.common.utilities.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    // ⚡ StateFlow holds the current state (Loading, Success, or Error)
    private val _loginState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val loginState: StateFlow<UiState<String>> = _loginState

    private val _registerState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val registerState: StateFlow<UiState<String>> = _registerState

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            val result = repository.loginUser(email, pass)
            _loginState.value = result
        }
    }

    fun register(email: String, pass: String, name: String) {
        viewModelScope.launch {
            _registerState.value = UiState.Loading
            val result = repository.registerUser(email, pass, name)
            _registerState.value = result
        }
    }
}