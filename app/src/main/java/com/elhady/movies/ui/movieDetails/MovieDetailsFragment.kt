package com.elhady.movies.ui.movieDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentMovieDetailsBinding
import com.elhady.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_movie_details
    override val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var detailAdapter: DetailsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailAdapter = DetailsAdapter(mutableListOf(), viewModel)
        binding.recyclerView.adapter = detailAdapter

        collectMovieDetailsItems()
    }

    private fun collectMovieDetailsItems() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.detailsUiState.collect {
                detailAdapter.setItems(mutableListOf(it.movieDetailsResult))
            }
        }
    }
}