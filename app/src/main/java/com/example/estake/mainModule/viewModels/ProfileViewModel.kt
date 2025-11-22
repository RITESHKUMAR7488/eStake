package com.example.estake.mainModule.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.estake.common.utilities.UiState
import com.example.estake.mainModule.adapters.PortfolioStat
import com.example.estake.mainModule.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    // 1. Portfolio Stats State
    private val _statsState = MutableStateFlow<UiState<List<PortfolioStat>>>(UiState.Loading)
    val statsState: StateFlow<UiState<List<PortfolioStat>>> = _statsState

    // 2. User Profile State
    private val _userState = MutableStateFlow<UiState<Map<String, String>>>(UiState.Loading)
    val userState: StateFlow<UiState<Map<String, String>>> = _userState

    init {
        loadProfileData()
    }

    fun loadProfileData() {
        viewModelScope.launch {
            // Fetch Stats
            _statsState.value = UiState.Loading
            _statsState.value = repository.getPortfolioStats()

            // Fetch User Info
            _userState.value = repository.getUserProfile()
        }
    }
}