package com.elhady.movies.feature.profile.presentation.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.elhady.movies.core.domain.model.account.ListName
import com.elhady.movies.core.domain.model.account.ListType
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.base.animationRes
import com.elhady.movies.core.ui.navigation.Navigator
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
    override val viewModelVariableId: Int = BR.viewModel

    companion object {
        private const val PREF_THEME_STATE = "night_mode_state"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectState()
        setListeners()
        changeAppTheme()
    }

    private fun setListeners() {
        binding.textViewFavorite.setOnClickListener { viewModel.onEvent(ProfileUiEvent.FavoriteClicked) }
        binding.textViewWatchlist.setOnClickListener { viewModel.onEvent(ProfileUiEvent.WatchlistClicked) }
        binding.textViewWatchHistory.setOnClickListener { viewModel.onEvent(ProfileUiEvent.WatchHistoryClicked) }
        binding.textViewMylists.setOnClickListener { viewModel.onEvent(ProfileUiEvent.MyListsClicked) }
        binding.textViewLogout.setOnClickListener { viewModel.onEvent(ProfileUiEvent.LogoutClicked) }
        binding.buttonLogin.setOnClickListener { viewModel.onEvent(ProfileUiEvent.LoginClicked) }
        binding.textviewErrorOccurred.setOnClickListener { viewModel.onEvent(ProfileUiEvent.RetryClicked) }
        binding.buttonRetry.setOnClickListener { viewModel.onEvent(ProfileUiEvent.RetryClicked) }
    }

    private fun collectState() {
        collectFlow(viewModel.state) { render(it) }
    }

    private fun render(state: ProfileUiState) {
        renderErrorAnimation(state)
    }

    private fun renderErrorAnimation(state: ProfileUiState) {
        val errors = state.errors
        if (errors == null) {
            binding.lottieAnimation.cancelAnimation()
            return
        }
        binding.lottieAnimation.setAnimation(errors.animationRes)
        binding.lottieAnimation.playAnimation()
    }

    override fun onEffect(effect: ProfileUiEffect) {
        when (effect) {
            ProfileUiEffect.NavigateToFavoriteScreen -> {
                navigator.navigateToMyListDetails(0, ListType.MOVIE.name, ListName.FAVORITE.name)
            }

            ProfileUiEffect.NavigateToWatchlistScreen -> {
                navigator.navigateToMyListDetails(0, ListType.MOVIE.name, ListName.WATCHLIST.name)
            }

            ProfileUiEffect.NavigateToWatchHistoryScreen -> navigator.navigateToWatchHistory()
            ProfileUiEffect.NavigateToMyListsScreen -> navigator.navigateToMyList()
            ProfileUiEffect.ShowLogoutDialog -> showConfirmDialog()
            ProfileUiEffect.NavigateToLogin -> navigator.navigateToLogin()
            is ProfileUiEffect.ShowSnackBar -> showSnackBar(effect.message)
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
