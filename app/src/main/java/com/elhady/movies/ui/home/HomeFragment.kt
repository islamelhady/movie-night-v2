package com.elhady.movies.ui.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ConcatAdapter
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentHomeBinding
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.ui.home.adapters.CategoryAdapter
import com.elhady.movies.ui.home.adapters.HorizontalCategoryAdapter
import com.elhady.movies.ui.home.adapters.HorizontalMovieImageAdapter
import com.elhady.movies.ui.home.adapters.MovieImageAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val layoutIdFragment: Int = R.layout.fragment_home
    override val viewModelClass = HomeViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val config = ConcatAdapter.Config.Builder().apply {
            setIsolateViewTypes(false)
        }.build()
        val concatAdapter = ConcatAdapter(
            config,
            HorizontalCategoryAdapter(CategoryAdapter(emptyList()), viewModel),
            HorizontalCategoryAdapter(CategoryAdapter(emptyList()), viewModel),
            HorizontalCategoryAdapter(CategoryAdapter(emptyList()), viewModel),
            HorizontalCategoryAdapter(CategoryAdapter(emptyList()), viewModel),
            HorizontalCategoryAdapter(CategoryAdapter(emptyList()), viewModel),

        )

        binding.recyclerView.adapter = concatAdapter
    }

}