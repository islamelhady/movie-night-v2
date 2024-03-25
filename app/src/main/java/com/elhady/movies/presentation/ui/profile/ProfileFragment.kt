package com.elhady.movies.presentation.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseFragment
import com.elhady.movies.core.bases.ListName
import com.elhady.movies.core.bases.ListType
import com.elhady.movies.databinding.FragmentProfileBinding
import com.elhady.movies.presentation.viewmodel.profile.ProfileUIState
import com.elhady.movies.presentation.viewmodel.profile.ProfileUiEvent
import com.elhady.movies.presentation.viewmodel.profile.ProfileViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileUIState, ProfileUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels()

    companion object {
        private const val PREF_THEME_STATE = "night_mode_state"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeAppTheme()
    }

    override fun onEvent(event: ProfileUiEvent) {
        when (event) {
            ProfileUiEvent.NavigateToFavoriteScreen -> {
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToMyListDetailsFragment(
                        listType = ListType.MOVIE.name,
                        listId = 0,
                        listName = ListName.FAVORITE.name,
                    )
                )
            }

            ProfileUiEvent.NavigateToWatchlistScreen -> {
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToMyListDetailsFragment(
                        listType = ListType.MOVIE.name,
                        listId = 0,
                        listName = ListName.WATCHLIST.name,
                    )
                )
            }

            ProfileUiEvent.NavigateToWatchHistoryScreen -> {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToWatchHistoryFragment())
            }

            ProfileUiEvent.NavigateToMyListsScreen -> {
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToMyListFragment()
                )
            }

            ProfileUiEvent.Logout -> {
                showConfirmDialog()
            }

            is ProfileUiEvent.NavigateWithLink -> {
                findNavController().navigate(event.link)
            }
        }
    }

    private fun showConfirmDialog(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.logout))
            .setMessage(getString(R.string.do_u_wanna_leave_us))
            .setPositiveButton(getString(R.string.confirm)) { _, _ ->
                viewModel.logout()
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
            }
            .setNeutralButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()

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
}