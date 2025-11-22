package com.example.estake

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.estake.databinding.ActivityMainBinding
import com.example.estake.mainModule.uis.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Default: Load Home & Lock Drawer
        loadFragment(HomeFragment())
        lockDrawer()

        // 2. Handle Bottom Nav Clicks
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    lockDrawer() // 🔒 Cannot open drawer here
                }
                R.id.nav_search -> {
                    loadFragment(SearchFragment())
                    lockDrawer() // 🔒 Cannot open drawer here
                }
                R.id.nav_invest -> {
                    loadFragment(InvestFragment())
                    lockDrawer() // 🔒 Cannot open drawer here
                }
                R.id.nav_reels -> {
                    loadFragment(ReelsFragment())
                    lockDrawer() // 🔒 Cannot open drawer here
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    unlockDrawer() // 🔓 ENABLE drawer only here!
                }
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    // 🔒 Helper: Disable Swipe
    private fun lockDrawer() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    // 🔓 Helper: Enable Swipe
    private fun unlockDrawer() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    // Allow Fragments to open the drawer programmatically
    fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.END)
    }
}