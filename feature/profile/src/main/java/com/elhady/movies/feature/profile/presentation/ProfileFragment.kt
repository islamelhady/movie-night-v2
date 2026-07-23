package com.elhady.movies.feature.profile.presentation

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.elhady.movies.feature.profile.BR
import com.elhady.movies.feature.profile.R
import com.elhady.movies.core.ui.R as CoreUiR
import com.elhady.movies.core.common.bases.BaseFragment
import com.elhady.movies.core.common.bases.ListName
import com.elhady.movies.core.common.bases.ListType
import com.elhady.movies.feature.profile.databinding.FragmentProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileUIState, ProfileUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels()
    override val viewModelVariableId: Int = BR.viewModel

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
                val request = NavDeepLinkRequest.Builder
                    .fromUri(Uri.parse("movie://my_list_details/0/${ListType.MOVIE.name}/${ListName.FAVORITE.name}"))
                    .build()
                findNavController().navigate(request)
            }

            ProfileUiEvent.NavigateToWatchlistScreen -> {
                val request = NavDeepLinkRequest.Builder
                    .fromUri(Uri.parse("movie://my_list_details/0/${ListType.MOVIE.name}/${ListName.WATCHLIST.name}"))
                    .build()
                findNavController().navigate(request)
            }

            ProfileUiEvent.NavigateToWatchHistoryScreen -> {
                val request = NavDeepLinkRequest.Builder
                    .fromUri(Uri.parse("movie://watch_history"))
                    .build()
                findNavController().navigate(request)
            }

            ProfileUiEvent.NavigateToMyListsScreen -> {
                val request = NavDeepLinkRequest.Builder
                    .fromUri(Uri.parse("movie://my_list"))
                    .build()
                findNavController().navigate(request)
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
            .setTitle(getString(CoreUiR.string.logout))
            .setMessage(getString(CoreUiR.string.do_u_wanna_leave_us))
            .setPositiveButton(getString(CoreUiR.string.confirm)) { _, _ ->
                viewModel.logout()
                val request = NavDeepLinkRequest.Builder.fromUri(Uri.parse("movie://login")).build()
                findNavController().navigate(request)
            }
            .setNeutralButton(getString(CoreUiR.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()

    }


    private fun changeAppTheme() {
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val switchButtonTheme = binding.switchBottonTheme
        val savedThemeState = sharedPreferences.getBoolean(PREF_THEME_STATE, false)
        switchButtonTheme.isChecked = savedThemeState

        switchButtonTheme.setOnCheckedChangeListener { _, iChecked ->
            sharedPreferences.edit().putBoolean(PREF_THEME_STATE, iChecked).apply()
            if (iChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}