package com.example.estake

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.estake.common.utilities.PreferenceManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class EstakeApplication : Application() {

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate() {
        super.onCreate()

        // ⚡ Apply Theme immediately on startup
        val isDarkMode = preferenceManager.getBoolean(PreferenceManager.KEY_IS_DARK_MODE)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}