package com.elhady.movies.presentation.ui.home.adapter

import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.home.HomeListener
import com.elhady.movies.presentation.viewmodel.home.UpComingMoviesUiState

class UpComingAdapter(
    upComingList: List<UpComingMoviesUiState>, listener: HomeListener
) : BaseAdapter<UpComingMoviesUiState>(upComingList,listener) {
    override val layoutID = R.layout.home_item_image_slider
}
