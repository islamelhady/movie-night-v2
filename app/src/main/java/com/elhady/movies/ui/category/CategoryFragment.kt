package com.elhady.movies.ui.category

import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentCategoryBinding
import com.elhady.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_category
    override val viewModel: CategoryViewModel by viewModels()




}