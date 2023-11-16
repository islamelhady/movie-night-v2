package com.elhady.movies.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentProfileBinding
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.utilities.collectLast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectEvents()
    }

    private fun collectEvents() {
        collectLast(viewModel.profileUIEvent) { event ->
            event?.getContentIfNotHandled()?.let {
                onEvent(it)
            }
        }
    }

    private fun onEvent(event: ProfileUiEvent) {
        val action = when (event) {
            ProfileUiEvent.DialogLogoutEvent -> ProfileFragmentDirections.actionProfileFragmentToLogoutDialog()
            ProfileUiEvent.RatedMoviesEvent -> ProfileFragmentDirections.actionProfileFragmentToRatingFragment()
            ProfileUiEvent.WatchHistoryEvent -> ProfileFragmentDirections.actionProfileFragmentToWatchHistoryFragment()
            ProfileUiEvent.LoginEvent -> ProfileFragmentDirections.actionProfileFragmentToLoginFragment(2)
        }
        findNavController().navigate(action)
    }


}