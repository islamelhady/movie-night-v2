package com.elhady.ui.reviews

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.base.BaseFragment
import com.elhady.ui.R
import com.elhady.ui.databinding.FragmentReviewsBinding
import com.elhady.viewmodel.reviews.ReviewInteraction
import com.elhady.viewmodel.reviews.ReviewsUiState
import com.elhady.viewmodel.reviews.ReviewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsFragment : BaseFragment<FragmentReviewsBinding, ReviewsUiState, ReviewInteraction>() {
    override val layoutIdFragment: Int = R.layout.fragment_reviews
    override val viewModel: ReviewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(true)
        binding.recyclerReview.adapter = ReviewAdapter(mutableListOf(), viewModel)
    }

    override fun onEvent(event: ReviewInteraction) {
        TODO("Not yet implemented")
    }


}