package com.example.estake.authModule.repositories

import com.example.estake.authModule.models.UserModel
import com.example.estake.common.utilities.UiState

interface AuthRepository {
    suspend fun loginUser(email: String, pass: String): UiState<String>
    // ⚡ Now accepts UserModel instead of just strings
    suspend fun registerUser(userModel: UserModel, pass: String): UiState<String>
}