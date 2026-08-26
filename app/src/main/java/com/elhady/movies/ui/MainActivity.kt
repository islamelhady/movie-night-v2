package com.elhady.movies.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.elhady.movies.R
import com.elhady.movies.core.domain.usecase.common.GetThemeUseCase
import com.elhady.movies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var getThemeUseCase: GetThemeUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAppTheme()
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


    private fun setAppTheme() {
        if (getThemeUseCase()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

}
