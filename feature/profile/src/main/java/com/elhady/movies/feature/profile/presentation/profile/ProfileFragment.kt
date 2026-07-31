package com.elhady.movies.feature.profile.presentation.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.profile.BR
import com.elhady.movies.feature.profile.R
import com.elhady.movies.core.ui.R as CoreUiR
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.domain.model.account.ListName
import com.elhady.movies.core.domain.model.account.ListType
import com.elhady.movies.feature.profile.databinding.FragmentProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileUiState, ProfileUiEvent>() {

    @Inject
    lateinit var navigator: Navigator

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
                navigator.navigateToMyListDetails(
                    0,
                    ListType.MOVIE.name,
                    ListName.FAVORITE.name
                )
            }

            ProfileUiEvent.NavigateToWatchlistScreen -> {
                navigator.navigateToMyListDetails(
                    0,
                    ListType.MOVIE.name,
                    ListName.WATCHLIST.name
                )
            }

            ProfileUiEvent.NavigateToWatchHistoryScreen -> {
                navigator.navigateToWatchHistory()
            }

            ProfileUiEvent.NavigateToMyListsScreen -> {
                navigator.navigateToMyList()
            }

            ProfileUiEvent.Logout -> {
                showConfirmDialog()
            }

            is ProfileUiEvent.NavigateWithLink -> {
                navigator.navigateToLogin()
            }
        }
    }

    private fun showConfirmDialog(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(CoreUiR.string.logout))
            .setMessage(getString(CoreUiR.string.do_u_wanna_leave_us))
            .setPositiveButton(getString(CoreUiR.string.confirm)) { _, _ ->
                viewModel.logout()
                navigator.navigateToLogin()
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
