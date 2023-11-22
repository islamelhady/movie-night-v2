package com.elhady.movies.ui.movieDetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentMovieDetailsBinding
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.utilities.collectLast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_movie_details
    override val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var detailAdapter: DetailsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        collectMovieDetailsItems()
        collectEvents()
    }


    private fun setupAdapter() {
        detailAdapter = DetailsAdapter(mutableListOf(), viewModel)
        binding.recyclerView.adapter = detailAdapter
    }

    private fun collectMovieDetailsItems() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                detailAdapter.setItems(viewModel.state.value.detailsItemsResult)
            }
        }
    }

    private fun collectEvents() {
        collectLast(viewModel.event) {
            onEvent(it)
        }
    }

    private fun onEvent(event: MovieDetailsUiEvent) {
        var action: NavDirections? = null

        when (event) {
            is MovieDetailsUiEvent.ClickPlayTrailerEvent -> {
                action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentToVideoFragment(
                    viewModel.args.movieID,
                    MediaType.MOVIES
                )
            }

            MovieDetailsUiEvent.ClickBackButton -> findNavController().popBackStack()

            is MovieDetailsUiEvent.ClickMovieEvent -> {
                viewModelStore.clear()
                action =
                    MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(event.movieId)
            }

            is MovieDetailsUiEvent.ClickCastEvent -> {
                action =
                    MovieDetailsFragmentDirections.actionMovieDetailsFragmentToActorDetailsFragment(
                        event.castId
                    )
            }

            MovieDetailsUiEvent.ClickSeeReviewsEvent -> action =
                MovieDetailsFragmentDirections.actionMovieDetailsFragmentToReviewsFragment(
                    mediaId = viewModel.args.movieID,
                    mediaType = MediaType.MOVIES
                )

            MovieDetailsUiEvent.MessageAppear -> Toast.makeText(
                context,
                R.string.submit_toast,
                Toast.LENGTH_LONG
            ).show()

            MovieDetailsUiEvent.ClickFavourite -> action =
                MovieDetailsFragmentDirections.actionMovieDetailsFragmentToSaveMovieDialog(viewModel.args.movieID)
        }

        action?.let {
            findNavController().navigate(it)
        }

    }
}