package com.elhady.movies.feature.details.presentation.ui.peopledetails

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.common.bases.BaseFragment
import com.elhady.movies.feature.details.databinding.FragmentPeopleDetailsBinding
import com.elhady.movies.feature.details.presentation.peopledetails.PeopleDetailsUiEvent
import com.elhady.movies.feature.details.presentation.peopledetails.PeopleDetailsViewModel
import com.elhady.movies.feature.details.presentation.peopledetails.PersonDetailsUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleDetailsFragment :
    BaseFragment<FragmentPeopleDetailsBinding, PersonDetailsUiState, PeopleDetailsUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_people_details
    override val viewModel: PeopleDetailsViewModel by viewModels()
    override val viewModelVariableId: Int = BR.viewModel
    private lateinit var peopleMoviesAdapter: PeopleDetailsRecyclerAdapter
    private lateinit var peopleTvShowsAdapter: PeopleDetailsRecyclerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapters()
        getData()
    }

    private fun setAdapters() {
        peopleMoviesAdapter = PeopleDetailsRecyclerAdapter(mutableListOf(), viewModel)
        binding.recyclerViewPeopleMovies.adapter = peopleMoviesAdapter

        peopleTvShowsAdapter = PeopleDetailsRecyclerAdapter(mutableListOf(), viewModel)
        binding.recyclerViewPeopleTvShows.adapter = peopleTvShowsAdapter
    }

    private fun getData() {
        collectLatest {
            viewModel.state.collect { state ->
                peopleMoviesAdapter.setItems(state.movies)
                peopleTvShowsAdapter.setItems(state.tvShows)
                if (state.onErrors.isNotEmpty()) {
                    state.onErrors.last().let {
                        showSnackBar(it)
                    }
                }
            }
        }
    }

    override fun onEvent(event: PeopleDetailsUiEvent) {
        when (event) {
            PeopleDetailsUiEvent.BackNavigate -> findNavController().popBackStack()
            is PeopleDetailsUiEvent.ClickMovieEvent -> navigateToMovieDetails(event.itemId)

            is PeopleDetailsUiEvent.ClickTvShowsEvent -> navigateToTvShowDetails(event.itemId)
        }
    }

    private fun navigateToMovieDetails(id: Int) {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://movie_details/$id"))
            .build()
        findNavController().navigate(request)
    }

    private fun navigateToTvShowDetails(id: Int) {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://tv_details/$id"))
            .build()
        findNavController().navigate(request)
    }

}
