package com.elhady.movies.ui.favorite.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentFavListDetailsBinding
import com.elhady.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavListDetailsFragment : BaseFragment<FragmentFavListDetailsBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_fav_list_details
    override val viewModel: FavListDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
    }

    private fun setupAdapter() {
        binding.recyclerListDetails.adapter = ListDetailsAdapter(mutableListOf(), viewModel)
    }

}