package com.elhady.movies.feature.details.presentation.moviedetails.adapter

import com.elhady.movies.core.ui.state.MediaVerticalUiState
import com.elhady.movies.core.ui.state.PeopleUiState
import com.elhady.movies.feature.details.presentation.moviedetails.ReviewUiState
import com.elhady.movies.feature.details.presentation.moviedetails.UpperUiState

sealed interface MovieDetailsItem {
    data class Upper(val upperUiState: UpperUiState) : MovieDetailsItem
    data class People(val list: List<PeopleUiState>) : MovieDetailsItem

    data class Recommended(val list: List<MediaVerticalUiState>,
                           val isEmptyReviews:Boolean,
                           val movieInt: Int,
                           val totalReviews:Int,
                           val totalPages: Boolean
    ) : MovieDetailsItem

    data class Reviews(val list: ReviewUiState) : MovieDetailsItem
}
