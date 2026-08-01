package com.elhady.movies.feature.details.presentation.episodedetails

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.details.databinding.FragmentEpisodeDetailsBinding
import com.elhady.movies.core.ui.adapter.PeopleAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EpisodeDetailsFragment :
    BaseFragment<FragmentEpisodeDetailsBinding, EpisodeDetailsUiState, EpisodeDetailsUiEvent>(),
    BottomSheetListener {

    @Inject
    lateinit var navigator: Navigator

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
            showSnackBar(getString(com.elhady.movies.core.ui.R.string.you_re_not_logged_in_to_rate))
        }
    }

    private fun showBottomSheet() {
        val bottomSheet = EpisodeRateBottomSheet()
        bottomSheet.show(childFragmentManager, "BOTTOM")
        bottomSheet.setListener(this)
    }


    private fun navigateToPlayFullScreen(videoKey: String) {
        navigator.navigateToTrailer(videoKey)
    }

    override fun onApplyRateBottomSheet() {
        viewModel.setRating()
    }

    override fun updateRatingValue(rate: Float) {
        viewModel.updateRatingState(rate)
    }

    private fun setAdapter() {
        collectFlow(viewModel.state) { state ->
            peopleAdapter.setItems(state.cast)
        }
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
