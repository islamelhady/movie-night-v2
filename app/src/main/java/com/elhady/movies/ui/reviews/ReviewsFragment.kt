package com.elhady.movies.ui.reviews

import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentReviewsBinding
import com.elhady.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsFragment : BaseFragment<FragmentReviewsBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_reviews
    override val viewModel: ReviewsViewModel by viewModels()


}