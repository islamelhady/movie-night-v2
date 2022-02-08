package com.elhady.movies.ui.home


import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentHomeBinding
import com.elhady.movies.ui.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(){

    override val layoutIdFragment: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()


}