package com.elhady.movies.feature.details.presentation.episodedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.core.ui.adapter.PeopleAdapter
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.interaction.PeopleAdapterListener
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.details.R
import com.elhady.movies.feature.details.databinding.FragmentEpisodeDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

@AndroidEntryPoint
class EpisodeDetailsFragment :
    BaseFragment<FragmentEpisodeDetailsBinding, EpisodeDetailsUiState, EpisodeDetailsUiEffect>(),
    EpisodeDetailsListener, PeopleAdapterListener, BottomSheetListener {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_episode_details
    override val viewModel: EpisodeDetailsViewModel by viewModels()

    private var inlineYoutubePlayer: YouTubePlayer? = null
    private var overlayYoutubePlayer: YouTubePlayer? = null
    private var loadedInlineVideoKey: String? = null
    private var loadedOverlayVideoKey: String? = null

    private val peopleAdapter: PeopleAdapter by lazy {
        PeopleAdapter(this)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.addObserver(binding.includePlayerOverlay.youtubePlayerView)
        viewLifecycleOwner.lifecycle.addObserver(binding.youtubePlayer)
        initYoutubePlayers()
        binding.listener = this

        setupPeopleAdapter()
        setupSwipeRefresh()
    }

    private fun setupPeopleAdapter() {
        binding.recyclerViewPeople.adapter = peopleAdapter
    }

    private fun setupSwipeRefresh() {
        binding.swipeToRefreshLayout.setOnRefreshListener {
            viewModel.onEvent(EpisodeDetailsUiEvent.Refresh)
        }
    }

    override fun render(state: EpisodeDetailsUiState) {
        handleInlinePlayerState(state.trailerKey)
        handleOverlayPlayerState(state.isPlayerVisible, state.trailerKey)
        binding.state = state
        binding.swipeToRefreshLayout.isRefreshing = state.isRefreshing
        peopleAdapter.submitList(state.cast)
    }

    private fun initYoutubePlayers() {
        binding.youtubePlayer.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                this@EpisodeDetailsFragment.inlineYoutubePlayer = youTubePlayer
                render(viewModel.state.value)
            }
        })

        binding.includePlayerOverlay.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                this@EpisodeDetailsFragment.overlayYoutubePlayer = youTubePlayer
                render(viewModel.state.value)
            }
        })
    }

    private fun handleInlinePlayerState(videoKey: String) {
        if (videoKey.isNotEmpty()) {
            if (loadedInlineVideoKey != videoKey && inlineYoutubePlayer != null) {
                inlineYoutubePlayer?.cueVideo(videoKey, 0f)
                loadedInlineVideoKey = videoKey
            }
        }
    }

    private fun handleOverlayPlayerState(isPlayerVisible: Boolean, videoKey: String) {
        if (isPlayerVisible && videoKey.isNotEmpty()) {
            if (loadedOverlayVideoKey != videoKey && overlayYoutubePlayer != null) {
                overlayYoutubePlayer?.cueVideo(videoKey, 0f)
                loadedOverlayVideoKey = videoKey
            }
        } else {
            if (loadedOverlayVideoKey != null) {
                overlayYoutubePlayer?.pause()
                loadedOverlayVideoKey = null
            }
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

    override fun onClickDismissPlayer() {
        viewModel.onEvent(EpisodeDetailsUiEvent.DismissPlayerClicked)
    }

    override fun onClickRetry() {
        viewModel.onEvent(
            EpisodeDetailsUiEvent.RetryClicked
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

    override fun onDestroyView() {
        super.onDestroyView()
        inlineYoutubePlayer = null
        overlayYoutubePlayer = null
        loadedInlineVideoKey = null
        loadedOverlayVideoKey = null
    }
}
