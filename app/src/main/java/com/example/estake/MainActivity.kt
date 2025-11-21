package com.example.estake

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.estake.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // 1. Define the binding variable
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 2. Inflate using ViewBinding (Correct method)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}