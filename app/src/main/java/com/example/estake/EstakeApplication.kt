package com.example.estake

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EstakeApplication : Application() {
    // This class is the "Power Button" for Hilt.
    // Without @HiltAndroidApp, nothing works.
}