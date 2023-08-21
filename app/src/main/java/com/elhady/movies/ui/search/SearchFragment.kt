package com.elhady.movies.ui.search

import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentSearchBinding
import com.elhady.movies.ui.base.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    override val layoutIdFragment: Int = R.layout.fragment_search
    override val viewModelClass = SearchViewModel::class.java

}