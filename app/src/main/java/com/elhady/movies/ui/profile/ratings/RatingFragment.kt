package com.elhady.movies.ui.profile.ratings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentRatingBinding
import com.elhady.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatingFragment : BaseFragment<FragmentRatingBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_rating
    override val viewModel: RatingViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
    }

    private fun setupAdapter() {
        binding.recyclerMyRating.adapter = MyRatingAdapter(mutableListOf(), viewModel)
    }
}