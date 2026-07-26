package com.elhady.movies.feature.details.presentation.ui.peopledetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.common.bases.BaseFragment
import com.elhady.movies.feature.details.databinding.FragmentPeopleDetailsBinding
import com.elhady.movies.feature.details.presentation.peopledetails.PeopleDetailsUiEvent
import com.elhady.movies.feature.details.presentation.peopledetails.PeopleDetailsViewModel
import com.elhady.movies.feature.details.presentation.peopledetails.PersonDetailsUiState
import com.elhady.movies.core.common.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PeopleDetailsFragment :
    BaseFragment<FragmentPeopleDetailsBinding, PersonDetailsUiState, PeopleDetailsUiEvent>() {

    @Inject
    lateinit var navigator: Navigator

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
            PeopleDetailsUiEvent.BackNavigate -> navigator.navigateBack()
            is PeopleDetailsUiEvent.ClickMovieEvent -> navigator.navigateToMovieDetails(event.itemId)

            is PeopleDetailsUiEvent.ClickTvShowsEvent -> navigator.navigateToTvDetails(event.itemId)
        }
    }

}
