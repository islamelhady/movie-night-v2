package com.elhady.ui.profile.ratings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.base.BaseFragment
import com.elhady.ui.R
import com.elhady.ui.databinding.FragmentRatingBinding
import com.elhady.viewmodel.profile.ratings.MyRateUiState
import com.elhady.viewmodel.profile.ratings.MyRatingUiEvent
import com.elhady.viewmodel.profile.ratings.RatingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatingFragment : BaseFragment<FragmentRatingBinding, MyRateUiState, MyRatingUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_rating
    override val viewModel: RatingViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(true)
        setupAdapter()
    }

    private fun setupAdapter() {
        binding.recyclerMyRating.adapter = MyRatingAdapter(mutableListOf(), viewModel)
    }

    override fun onEvent(event: MyRatingUiEvent) {
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