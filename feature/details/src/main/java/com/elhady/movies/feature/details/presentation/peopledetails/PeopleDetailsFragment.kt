package com.elhady.movies.feature.details.presentation.peopledetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.core.common.MediaType
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
    }

    private fun setupAdapters() {
        peopleMoviesAdapter = PeopleDetailsRecyclerAdapter(
            listener = this,
        )

        peopleTvShowsAdapter = PeopleDetailsRecyclerAdapter(
            listener = this,
        )

        binding.recyclerViewPeopleMovies.adapter = peopleMoviesAdapter
        binding.recyclerViewPeopleTvShows.adapter = peopleTvShowsAdapter
    }

    override fun render(state: PeopleDetailsUiState) {
        binding.state = state

        peopleMoviesAdapter.submitList(state.movies)
        peopleTvShowsAdapter.submitList(state.tvShows)
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
        type: MediaType,
    ) {
        when (type) {
            MediaType.MOVIE -> {
                viewModel.onEvent(
                    PeopleDetailsUiEvent.MovieClicked(itemId)
                )
            }

            MediaType.TV_SHOW -> {
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