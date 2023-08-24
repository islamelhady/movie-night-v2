package com.elhady.movies.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.elhady.movies.R
import com.elhady.movies.data.Types
import com.elhady.movies.databinding.FragmentHomeBinding
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.ui.home.adapters.BannerAdapter
import com.elhady.movies.ui.home.adapters.CategoryAdapter
import com.elhady.movies.ui.home.adapters.HorizontalAdapter
import com.elhady.movies.ui.home.adapters.MovieImageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()


    private val homeAdapter by lazy {
        listOf(
            HorizontalAdapter<BannerAdapter>(Types.BannerType, viewModel),
            HorizontalAdapter<MovieImageAdapter>(Types.MovieType, viewModel),
            HorizontalAdapter<CategoryAdapter>(Types.CategoryType, viewModel)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val concatAdapter = ConcatAdapter(homeAdapter)
        binding.recyclerView.adapter = concatAdapter

    }
}