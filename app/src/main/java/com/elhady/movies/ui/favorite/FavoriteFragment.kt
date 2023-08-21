package com.elhady.movies.ui.favorite

import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentFavoriteBinding
import com.elhady.movies.ui.base.BaseFragment

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {

    override val layoutIdFragment: Int = R.layout.fragment_favorite
    override val viewModelClass = FavoriteViewModel::class.java


}