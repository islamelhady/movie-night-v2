package com.elhady.movies.ui.favorite

import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentFavoriteBinding
import com.elhady.movies.ui.base.BaseFragment

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_favorite
    override val viewModel: FavoriteViewModel by viewModels()


}