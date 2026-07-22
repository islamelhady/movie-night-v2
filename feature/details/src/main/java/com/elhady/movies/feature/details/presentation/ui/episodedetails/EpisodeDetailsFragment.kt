package com.elhady.movies.feature.details.presentation.ui.episodedetails

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.common.bases.BaseFragment
import com.elhady.movies.feature.details.databinding.FragmentEpisodeDetailsBinding
import com.elhady.movies.core.ui.adapters.PeopleAdapter
import com.elhady.movies.feature.details.presentation.episodedetails.EpisodeDetailsUiEvent
import com.elhady.movies.feature.details.presentation.episodedetails.EpisodeDetailsUiState
import com.elhady.movies.feature.details.presentation.episodedetails.EpisodeDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeDetailsFragment :
    BaseFragment<FragmentEpisodeDetailsBinding, EpisodeDetailsUiState, EpisodeDetailsUiEvent>(),
    BottomSheetListener {

    override val layoutIdFragment = R.layout.fragment_episode_details
    override val viewModel: EpisodeDetailsViewModel by viewModels()
    override val viewModelVariableId: Int = BR.viewModel
    private val peopleAdapter by lazy { PeopleAdapter(mutableListOf(), viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        binding.swipeToRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    override fun onEvent(event: EpisodeDetailsUiEvent) {
        when (event) {
            is EpisodeDetailsUiEvent.ClickToBack -> navigateToBack()
            is EpisodeDetailsUiEvent.ClickToRate -> checkIsLoggedInOrNot()
            is EpisodeDetailsUiEvent.ClickCast -> navigateToCastDetails(event.itemId)
            is EpisodeDetailsUiEvent.SubmitRating -> showSnackBar(event.message)
            is EpisodeDetailsUiEvent.ClickToPlayFullScreen -> navigateToPlayFullScreen(event.videoKey)
        }
    }

    private fun checkIsLoggedInOrNot() {
        val isLoggedIn = viewModel.state.value.isLoggedIn
        if (isLoggedIn) {
            showBottomSheet()
        } else {
            showSnackBar("You are not logged in \uD83D\uDE22, please log in to rate this episode");
        }
    }

    private fun showBottomSheet() {
        val bottomSheet = EpisodeRateBottomSheet()
        bottomSheet.show(childFragmentManager, "BOTTOM")
        bottomSheet.setListener(this)
    }


    private fun navigateToPlayFullScreen(videoKey: String) {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://trailer/$videoKey"))
            .build()
        findNavController().navigate(request)
    }

    override fun onApplyRateBottomSheet() {
        viewModel.setRating()
    }

    override fun updateRatingValue(rate: Float) {
        viewModel.updateRatingState(rate)
    }

    private fun setAdapter() {
        collectLatest { peopleAdapter.setItems(viewModel.state.value.cast) }
        binding.recyclerViewPeople.adapter = peopleAdapter
    }

    private fun navigateToBack() {
        findNavController().popBackStack()
    }

    private fun navigateToCastDetails(itemId: Int) {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://people_details/$itemId"))
            .build()
        findNavController().navigate(request)
    }
}
