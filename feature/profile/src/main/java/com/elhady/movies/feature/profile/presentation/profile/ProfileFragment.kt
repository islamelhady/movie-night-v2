package com.elhady.movies.feature.profile.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.elhady.movies.core.domain.model.account.ListName
import com.elhady.movies.core.domain.model.account.ListType
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.core.ui.util.loadProfileImage
import com.elhady.movies.feature.profile.R
import com.elhady.movies.feature.profile.databinding.FragmentProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.elhady.movies.core.ui.R as CoreUiR

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileUiState, ProfileUiEffect>(),
    ProfileListener {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.switchBottonTheme.setOnCheckedChangeListener { buttonView, isChecked ->
            // Only trigger event if the change comes from a user click
            if (buttonView.isPressed) {
                viewModel.onEvent(ProfileUiEvent.ThemeChanged(isChecked))
            }
        }
        binding.listener = this
    }

    override fun render(state: ProfileUiState) {
        binding.state = state
        renderTheme(state)
    }

    private fun renderTheme(state: ProfileUiState) {
        val targetMode = if (state.isDarkTheme) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        if (AppCompatDelegate.getDefaultNightMode() != targetMode) {
            AppCompatDelegate.setDefaultNightMode(targetMode)
        }
    }

    override fun onClickFavorite() {
        viewModel.onEvent(ProfileUiEvent.FavoriteClicked)
    }

    override fun onClickWatchlist() {
        viewModel.onEvent(ProfileUiEvent.WatchlistClicked)
    }

    override fun onClickWatchHistory() {
        viewModel.onEvent(ProfileUiEvent.WatchHistoryClicked)
    }

    override fun onClickMyRated() {
        viewModel.onEvent(ProfileUiEvent.RateClicked)
    }

    override fun onClickMyLists() {
        viewModel.onEvent(ProfileUiEvent.MyListsClicked)
    }

    override fun onClickLogout() {
        viewModel.onEvent(ProfileUiEvent.LogoutClicked)
    }

    override fun onClickLogin() {
        viewModel.onEvent(ProfileUiEvent.LoginClicked)
    }

    override fun onClickRetry() {
        viewModel.onEvent(ProfileUiEvent.RetryClicked)
    }

    override fun onEffect(effect: ProfileUiEffect) {
        when (effect) {
            ProfileUiEffect.NavigateToFavoriteScreen -> {
                navigator.navigateToMyListDetails(0, ListType.MOVIE.name, ListName.FAVORITE.name)
            }

            ProfileUiEffect.NavigateToWatchlistScreen -> {
                navigator.navigateToMyListDetails(0, ListType.MOVIE.name, ListName.WATCHLIST.name)
            }

            ProfileUiEffect.NavigateToRateScreen -> {
                navigator.navigateToMyRated()
            }

            ProfileUiEffect.NavigateToWatchHistoryScreen -> navigator.navigateToWatchHistory()
            ProfileUiEffect.NavigateToMyListsScreen -> navigator.navigateToMyList()
            ProfileUiEffect.ShowLogoutDialog -> showConfirmDialog()
            ProfileUiEffect.NavigateToLogin -> navigator.navigateToLogin()
            is ProfileUiEffect.ShowSnackBar -> showSnackBar(getString(effect.message))
        }
    }

    private fun showConfirmDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(CoreUiR.string.logout))
            .setMessage(getString(CoreUiR.string.do_u_wanna_leave_us))
            .setPositiveButton(getString(CoreUiR.string.confirm)) { _, _ ->
                viewModel.onEvent(ProfileUiEvent.LogoutConfirmed)
            }
            .setNeutralButton(getString(CoreUiR.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
