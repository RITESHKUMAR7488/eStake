package com.example.estake.mainModule.uis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.estake.R
import com.example.estake.common.utilities.PreferenceManager
import com.example.estake.common.utilities.UiState
import com.example.estake.databinding.FragmentProfileBinding
import com.example.estake.mainModule.adapters.PortfolioStatsAdapter
import com.example.estake.mainModule.adapters.UploadsAdapter
import com.example.estake.mainModule.viewModels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    // ⚡ Connects to the ViewModel (Architecture)
    private val viewModel: ProfileViewModel by viewModels()

    @Inject lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupHeader()
        setupLists()
        setupDrawerTrigger()

        // ⚡ Start listening to real data (or the delayed dummy data from Repository)
        observeData()
    }

    private fun setupHeader() {
        // 1. Check saved theme preference
        val isDark = preferenceManager.getBoolean(PreferenceManager.KEY_IS_DARK_MODE)
        updateThemeIcon(isDark)

        // 2. Toggle Logic
        binding.btnThemeToggle.setOnClickListener {
            val newMode = !isDark
            preferenceManager.putBoolean(PreferenceManager.KEY_IS_DARK_MODE, newMode)

            if (newMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            updateThemeIcon(newMode)
        }
    }

    private fun updateThemeIcon(isDark: Boolean) {
        if (isDark) {
            binding.btnThemeToggle.setImageResource(R.drawable.ic_sun)
        } else {
            binding.btnThemeToggle.setImageResource(R.drawable.ic_moon)
        }
    }

    private fun setupLists() {
        // 1. Initialize Portfolio List (Empty initially)
        binding.rvPortfolioStats.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 2. Initialize Uploads List (Static 5 slots for now)
        binding.rvUploads.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = UploadsAdapter(5)
        }
    }

    private fun setupDrawerTrigger() {
        binding.btnMenu.setOnClickListener {
            (activity as? com.example.estake.MainActivity)?.openDrawer()
        }
    }

    private fun observeData() {
        // ⚡ Lifecycle-aware collection (Best Practice)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // 1. Observe Portfolio Stats (Data from ViewModel)
                launch {
                    viewModel.statsState.collect { state ->
                        when (state) {
                            is UiState.Success -> {
                                // ⚡ Update the adapter when data arrives
                                binding.rvPortfolioStats.adapter = PortfolioStatsAdapter(state.data)
                            }
                            is UiState.Loading -> {
                                // Optional: Show shimmer here
                            }
                            is UiState.Failure -> {
                                // Handle error (Toast or Log)
                            }
                            else -> {}
                        }
                    }
                }

                // 2. Observe User Profile (Name, Email)
                launch {
                    viewModel.userState.collect { state ->
                        if (state is UiState.Success) {
                            binding.tvUserName.text = state.data["name"]
                            // If you add email to XML later:
                            // binding.tvUserEmail.text = state.data["email"]
                        }
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}