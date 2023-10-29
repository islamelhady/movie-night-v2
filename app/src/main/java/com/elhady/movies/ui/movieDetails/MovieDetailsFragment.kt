package com.elhady.movies.ui.movieDetails

import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentMovieDetailsBinding
import com.elhady.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_movie_details
    override val viewModel: MovieDetailsViewModel by viewModels()


}