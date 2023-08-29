package com.elhady.movies.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.data.remote.State
import com.elhady.movies.databinding.FragmentHomeBinding
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.ui.home.adapters.MovieImageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()
    private var adapterMovie: MovieImageAdapter? = null






    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        viewModel.popularMovie.observe(viewLifecycleOwner){
            when(it){
                is State.Success -> it.data?.items?.let {
                        it1 -> adapterMovie?.setItem(it1)
                }
                is  State.Error-> Log.e("HomeFragment", "Error" )
                is State.Loading -> Log.e("HomeFragment", "Loading" )
            }
        }
    }

    private fun setupAdapter() {
        adapterMovie = MovieImageAdapter(mutableListOf(), viewModel)
        binding.recyclerView.adapter = adapterMovie
    }


}