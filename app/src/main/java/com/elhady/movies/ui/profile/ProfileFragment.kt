package com.elhady.movies.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentProfileBinding
import com.elhady.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileUiState, ProfileUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(true)
        changeAppTheme()
    }

    override fun onEvent(event: ProfileUiEvent) {
        val action = when (event) {
            ProfileUiEvent.DialogLogoutEvent -> ProfileFragmentDirections.actionProfileFragmentToLogoutDialog()
            ProfileUiEvent.RatedMoviesEvent -> ProfileFragmentDirections.actionProfileFragmentToRatingFragment()
            ProfileUiEvent.WatchHistoryEvent -> ProfileFragmentDirections.actionProfileFragmentToWatchHistoryFragment()
            ProfileUiEvent.LoginEvent -> ProfileFragmentDirections.actionProfileFragmentToLoginFragment(2)
        }
        findNavController().navigate(action)
    }

    private fun changeAppTheme() {
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val switchButtonTheme = binding.switchBottonTheme
        val savedThemeState = sharedPreferences.getBoolean(PREF_THEME_STATE, false)
        switchButtonTheme.isChecked = savedThemeState

        switchButtonTheme.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean(PREF_THEME_STATE, isChecked).apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    companion object{
        private const val PREF_THEME_STATE = "theme"
    }


}