package com.elhady.movies

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.elhady.movies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeAppTheme()
    }

    override fun onResume() {
        super.onResume()

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment,
                R.id.tvFragment,
                R.id.exploreFragment,
                R.id.profileFragment -> showBottomNav()

                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        binding.bottomNavView.apply {
            visibility = View.VISIBLE
            animate().translationY(0f).setDuration(300).start()
        }
    }

    private fun hideBottomNav() {
        binding.bottomNavView.apply {
            animate().translationY(height.toFloat()).setDuration(300)
                .withEndAction { visibility = View.GONE }.start()
        }
    }


    private fun changeAppTheme() {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val savedThemeState = sharedPreferences.getBoolean(KEY_NIGHT_MODE_STATE, false)
        if (savedThemeState) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    companion object {
        const val KEY_NIGHT_MODE_STATE = "night_mode_state"
    }


}