package com.elhady.movies.ui.reviews

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentReviewsBinding
import com.elhady.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsFragment : BaseFragment<FragmentReviewsBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_reviews
    override val viewModel: ReviewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerReview.adapter = ReviewAdapter(emptyList(), viewModel)
    }


}