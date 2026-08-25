package com.elhady.movies.feature.details.presentation.peopledetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.details.R
import com.elhady.movies.feature.details.databinding.FragmentPeopleDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PeopleDetailsFragment :
    BaseFragment<FragmentPeopleDetailsBinding, PeopleDetailsUiState, PeopleDetailsUiEffect>(),
    PeopleDetailsListener {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_people_details
    override val viewModel: PeopleDetailsViewModel by viewModels()

    private lateinit var peopleMoviesAdapter: PeopleDetailsRecyclerAdapter
    private lateinit var peopleTvShowsAdapter: PeopleDetailsRecyclerAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.listener = this

        setupAdapters()
        collectState()
    }

    private fun setupAdapters() {
        peopleMoviesAdapter = PeopleDetailsRecyclerAdapter(
            items = emptyList(),
            listener = this,
        )

        peopleTvShowsAdapter = PeopleDetailsRecyclerAdapter(
            items = emptyList(),
            listener = this,
        )

        binding.recyclerViewPeopleMovies.adapter = peopleMoviesAdapter
        binding.recyclerViewPeopleTvShows.adapter = peopleTvShowsAdapter
    }

    private fun collectState() {
        collectFlow(viewModel.state) { state ->
            binding.state = state

            peopleMoviesAdapter.setItems(state.movies)
            peopleTvShowsAdapter.setItems(state.tvShows)
        }
    }

    override fun onEffect(effect: PeopleDetailsUiEffect) {
        when (effect) {
            PeopleDetailsUiEffect.NavigateBack -> {
                navigator.navigateBack()
            }

            is PeopleDetailsUiEffect.NavigateToMovieDetails -> {
                navigator.navigateToMovieDetails(effect.movieId)
            }

            is PeopleDetailsUiEffect.NavigateToTvDetails -> {
                navigator.navigateToTvDetails(effect.tvShowId)
            }

            is PeopleDetailsUiEffect.ShowSnackBar -> {
                showSnackBar(effect.message)
            }
        }
    }

    override fun onClickMedia(
        itemId: Int,
        type: PeopleDetailsUiState.MediaType,
    ) {
        when (type) {
            PeopleDetailsUiState.MediaType.MOVIE -> {
                viewModel.onEvent(
                    PeopleDetailsUiEvent.MovieClicked(itemId)
                )
            }

            PeopleDetailsUiState.MediaType.TV_SHOW -> {
                viewModel.onEvent(
                    PeopleDetailsUiEvent.TvShowClicked(itemId)
                )
            }
        }
    }

    override fun backNavigate() {
        viewModel.onEvent(
            PeopleDetailsUiEvent.BackClicked
        )
    }

    override fun onClickRetry() {
        viewModel.onEvent(
            PeopleDetailsUiEvent.RetryClicked
        )
    }
}