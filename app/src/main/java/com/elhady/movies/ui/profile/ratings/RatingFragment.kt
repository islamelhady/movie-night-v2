package com.elhady.movies.ui.profile.ratings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentRatingBinding
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.utilities.collectLast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatingFragment : BaseFragment<FragmentRatingBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_rating
    override val viewModel: RatingViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        collectEvent()
    }

    private fun setupAdapter() {
        binding.recyclerMyRating.adapter = MyRatingAdapter(mutableListOf(), viewModel)
    }

    fun collectEvent() {
        collectLast(viewModel.event) {
            onEvent(it)
        }
    }

    private fun onEvent(event: MyRatingUiEvent) {
        val action = when (event) {
            is MyRatingUiEvent.MovieEvent -> RatingFragmentDirections.actionRatingFragmentToMovieDetailsFragment(
                event.movieId
            )

            is MyRatingUiEvent.SeriesEvent -> RatingFragmentDirections.actionRatingFragmentToTvShowDetailsFragment(
                event.tvShowId
            )
        }
        findNavController().navigate(action)
    }
}