package com.elhady.movies.feature.watchlist.presentation.listcontents

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.core.common.MediaType
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.feature.watchlist.databinding.FragmentMyListDetailsBinding
import com.elhady.movies.feature.watchlist.presentation.listcontents.adapter.ListContentsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListContentsFragment :
    BaseFragment<FragmentMyListDetailsBinding, ListContentsUiState, ListContentsUiEffect>(),
    ListContentsAdapterListener, ListContentsListener {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int =
        R.layout.fragment_my_list_details

    override val viewModel: ListContentsViewModel by viewModels()

    private val adapter: ListContentsAdapter by lazy {
        ListContentsAdapter(
            items = emptyList(),
            listener = this
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        binding.listener = this
    }

    private fun setupRecyclerView() {
        binding.recyclerViewMyListDetails.adapter = adapter
    }


    override fun render(state: ListContentsUiState) {
        binding.state = state
        adapter.setItems(state.movies)
    }

    override fun onEffect(effect: ListContentsUiEffect) {
        when (effect) {

            is ListContentsUiEffect.NavigateToMovieContents -> {
                navigator.navigateToMovieDetails(
                    effect.movieId
                )
            }

            is ListContentsUiEffect.NavigateToTvShowContents -> {
                navigator.navigateToTvDetails(
                    effect.tvShowId
                )
            }

            ListContentsUiEffect.NavigateBack -> {
                navigator.navigateBack()
            }

            is ListContentsUiEffect.ShowSnackBar -> {
                showSnackBar(effect.message)
            }
        }
    }

    override fun onClickItem(itemId: Int, mediaType: MediaType) {
        if (mediaType == MediaType.TV_SHOW) {
            viewModel.onEvent(ListContentsUiEvent.TvShowClicked(itemId))
        } else {
            viewModel.onEvent(ListContentsUiEvent.MovieClicked(itemId))
        }
    }

    override fun onClickBackButton() {
        viewModel.onEvent(
            ListContentsUiEvent.BackClicked
        )
    }

    override fun onClickRetry() {
        viewModel.onEvent(
            ListContentsUiEvent.RetryClicked
        )
    }
}
