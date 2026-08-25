package com.elhady.movies.feature.profile.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.elhady.movies.core.domain.model.account.ListName
import com.elhady.movies.core.domain.model.account.ListType
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.base.animationRes
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.core.ui.util.loadProfileImage
import com.elhady.movies.feature.profile.BR
import com.elhady.movies.feature.profile.R
import com.elhady.movies.core.ui.R as CoreUiR
import com.elhady.movies.feature.profile.databinding.FragmentProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileUiState, ProfileUiEffect>() {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectState()
        setListeners()
    }

    private fun setListeners() {
        binding.textViewFavorite.setOnClickListener { viewModel.onEvent(ProfileUiEvent.FavoriteClicked) }
        binding.textViewWatchlist.setOnClickListener { viewModel.onEvent(ProfileUiEvent.WatchlistClicked) }
        binding.textViewWatchHistory.setOnClickListener { viewModel.onEvent(ProfileUiEvent.WatchHistoryClicked) }
        binding.textViewMyRated.setOnClickListener { viewModel.onEvent(ProfileUiEvent.RateClicked) }
        binding.textViewMylists.setOnClickListener { viewModel.onEvent(ProfileUiEvent.MyListsClicked) }
        binding.textViewLogout.setOnClickListener { viewModel.onEvent(ProfileUiEvent.LogoutClicked) }
        binding.buttonLogin.setOnClickListener { viewModel.onEvent(ProfileUiEvent.LoginClicked) }
        binding.buttonRetry.setOnClickListener { viewModel.onEvent(ProfileUiEvent.RetryClicked) }
        binding.switchBottonTheme.setOnCheckedChangeListener { buttonView, isChecked ->
            // Only trigger event if the change comes from a user click
            if (buttonView.isPressed) {
                viewModel.onEvent(ProfileUiEvent.ThemeChanged(isChecked))
            }
        }
    }

    private fun collectState() {
        collectFlow(viewModel.state) { render(it) }
    }

    private fun render(state: ProfileUiState) {
        renderLoading(state)
        renderError(state)
        renderProfile(state)
        renderLogin(state)
        renderTheme(state)
    }

    private fun renderLoading(state: ProfileUiState) {
        binding.progressBar.isVisible = state.isLoading
    }

    private fun renderError(state: ProfileUiState) {
        val isError = state.errors != null
        binding.groupError.isVisible = !state.isLoading && isError
        if (isError && !state.isLoading) {
            binding.lottieAnimation.setAnimation(state.errors!!.animationRes)
            binding.lottieAnimation.playAnimation()
        } else {
            binding.lottieAnimation.cancelAnimation()
        }
    }

    private fun renderProfile(state: ProfileUiState) {
        val isShowProfile = !state.isLoading && state.errors == null && state.isLogIn
        binding.whenUserLogin.isVisible = isShowProfile
        if (isShowProfile) {
            binding.textViewProfileUsername.text = state.username
            binding.imageViewProfile.loadProfileImage(state.avatarUrl)
        }
    }

    private fun renderLogin(state: ProfileUiState) {
        binding.whenUserNotLogin.isVisible = !state.isLoading && state.errors == null && !state.isLogIn
    }

    private fun renderTheme(state: ProfileUiState) {
        if (binding.switchBottonTheme.isChecked != state.isDarkTheme) {
            binding.switchBottonTheme.isChecked = state.isDarkTheme
        }

        val targetMode = if (state.isDarkTheme) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        if (AppCompatDelegate.getDefaultNightMode() != targetMode) {
            AppCompatDelegate.setDefaultNightMode(targetMode)
        }
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
