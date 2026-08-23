package com.elhady.movies.feature.details.presentation.episodedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.core.ui.adapter.PeopleAdapter
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.interaction.PeopleListener
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.details.R
import com.elhady.movies.feature.details.databinding.FragmentEpisodeDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EpisodeDetailsFragment :
    BaseFragment<FragmentEpisodeDetailsBinding, EpisodeDetailsUiState, EpisodeDetailsUiEffect>(),
    EpisodeDetailsListener, EpisodeListener, PeopleListener, BottomSheetListener {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_episode_details
    override val viewModel: EpisodeDetailsViewModel by viewModels()

    private val peopleAdapter: PeopleAdapter by lazy {
        PeopleAdapter(mutableListOf(), this)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.listener = this

        setupPeopleAdapter()
        setupSwipeRefresh()
        collectState()
    }

    private fun setupPeopleAdapter() {
        binding.recyclerViewPeople.adapter = peopleAdapter
    }

    private fun setupSwipeRefresh() {
        binding.swipeToRefreshLayout.setOnRefreshListener {
            viewModel.onEvent(EpisodeDetailsUiEvent.Refresh)
        }
    }

    private fun collectState() {
        collectFlow(viewModel.state) { state ->
            binding.state = state

            binding.swipeToRefreshLayout.isRefreshing =
                state.isRefreshing

            peopleAdapter.setItems(state.cast)
        }
    }

    override fun onEffect(effect: EpisodeDetailsUiEffect) {
        when (effect) {

            EpisodeDetailsUiEffect.NavigateBack -> {
                navigator.navigateBack()
            }

            is EpisodeDetailsUiEffect.NavigateToCastDetails -> {
                navigator.navigateToPeopleDetails(effect.personId)
            }

            is EpisodeDetailsUiEffect.NavigateToTrailer -> {
                navigator.navigateToTrailer(effect.videoKey)
            }

            EpisodeDetailsUiEffect.ShowRatingBottomSheet -> {
                showRatingBottomSheet()
            }

            is EpisodeDetailsUiEffect.ShowSnackBar -> {
                showSnackBar(effect.message)
            }
        }
    }

    override fun onClickBack() {
        viewModel.onEvent(
            EpisodeDetailsUiEvent.BackClicked
        )
    }

    override fun onClickRate() {
        viewModel.onEvent(
            EpisodeDetailsUiEvent.RateClicked
        )
    }

    override fun onClickPlayFullScreen(
        videoKey: String
    ) {
        viewModel.onEvent(
            EpisodeDetailsUiEvent.PlayFullScreenClicked(
                videoKey = videoKey
            )
        )
    }

    override fun onClickRetry() {
        viewModel.onEvent(
            EpisodeDetailsUiEvent.RetryClicked
        )
    }

    override fun onClickEpisode(id: Int) {
        viewModel.onEvent(
            EpisodeDetailsUiEvent.CastClicked(
                personId = id
            )
        )
    }

    override fun onClickPeople(id: Int) {
        viewModel.onEvent(
            EpisodeDetailsUiEvent.CastClicked(personId = id)
        )
    }

    private fun showRatingBottomSheet() {
        val bottomSheet = EpisodeRateBottomSheet()

        bottomSheet.setListener(this)

        bottomSheet.show(
            childFragmentManager,
            "EpisodeRateBottomSheet"
        )
    }


    override fun onApplyRateBottomSheet(rate: Float) {
        viewModel.onEvent(EpisodeDetailsUiEvent.RatingChanged(rate))
        viewModel.onEvent(EpisodeDetailsUiEvent.SubmitRating)
    }
}