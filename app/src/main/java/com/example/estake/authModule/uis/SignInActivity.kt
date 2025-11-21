package com.example.estake.authModule.uis

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.estake.MainActivity
import com.example.estake.authModule.viewModels.AuthViewModel
import com.example.estake.common.utilities.PreferenceManager
import com.example.estake.common.utilities.UiState
import com.example.estake.databinding.ActivitySignInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    // ⚡ Hilt automatically injects the ViewModel factory
    private val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Check if already logged in
        if (preferenceManager.getBoolean(PreferenceManager.KEY_IS_LOGGED_IN)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeState()
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val pass = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ⚡ Trigger the Coroutine in ViewModel
            viewModel.login(email, pass)
        }

        binding.tvRegister.setOnClickListener {
            // We will build this next
            // startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun observeState() {
        // ⚡ Collect the StateFlow (The Coroutines way to listen for updates)
        lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        binding.btnLogin.text = "Verifying..."
                        binding.btnLogin.isEnabled = false
                    }
                    is UiState.Success -> {
                        binding.btnLogin.text = "Success"
                        preferenceManager.putBoolean(PreferenceManager.KEY_IS_LOGGED_IN, true)

                        // Navigate to Main App
                        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                        finish()
                    }
                    is UiState.Failure -> {
                        binding.btnLogin.text = "Secure Login"
                        binding.btnLogin.isEnabled = true
                        Toast.makeText(this@SignInActivity, state.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}