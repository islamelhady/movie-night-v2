package com.elhady.movies.presentation.ui.home.adapter

import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.home.HomeListener
import com.elhady.movies.presentation.viewmodel.home.PopularPeopleUiState

class PopularPeopleAdapter(
    itemsPopularPeople: List<PopularPeopleUiState>,
    listener: HomeListener
) : BaseAdapter<PopularPeopleUiState>(itemsPopularPeople, listener) {
    override val layoutID = R.layout.home_item_popular_people

}