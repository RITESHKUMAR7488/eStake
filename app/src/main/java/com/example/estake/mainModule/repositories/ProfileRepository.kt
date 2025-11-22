package com.example.estake.mainModule.repositories

import com.example.estake.common.utilities.UiState
import com.example.estake.mainModule.adapters.PortfolioStat

interface ProfileRepository {
    // Fetches the stats (Properties, Rent, etc.)
    suspend fun getPortfolioStats(): UiState<List<PortfolioStat>>

    // Fetches the user's profile info (Name, Email, Avatar)
    suspend fun getUserProfile(): UiState<Map<String, String>>
}