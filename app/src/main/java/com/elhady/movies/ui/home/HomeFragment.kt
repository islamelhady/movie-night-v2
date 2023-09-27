package com.elhady.movies.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentHomeBinding
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.ui.home.adapters.PopularMovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()
    private var adapterMovie: PopularMovieAdapter? = null






    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

       viewLifecycleOwner.lifecycleScope.launch {
           viewModel.homeUiState.collect{
               adapterMovie?.setItem(it.popularUiState)
           }
       }
    }

    private fun setupAdapter() {
        adapterMovie = PopularMovieAdapter(mutableListOf(), viewModel)
        binding.recyclerView.adapter = adapterMovie
    }


}