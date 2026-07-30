package com.elhady.movies.feature.details.presentation.ui.moviedetails.adapter

import com.elhady.movies.core.ui.state.MediaVerticalUiState
import com.elhady.movies.core.ui.state.PeopleUiState
import com.elhady.movies.feature.details.presentation.moviedetails.ReviewUiState
import com.elhady.movies.feature.details.presentation.moviedetails.UpperUiState

sealed class MovieDetailsItem(val type: MovieDetailsType) {
    data class Upper(val upperUiState: UpperUiState) : MovieDetailsItem(MovieDetailsType.UPPER)
    data class People(val list: List<PeopleUiState>) : MovieDetailsItem(MovieDetailsType.PEOPLE)

    data class Recommended(val list: List<MediaVerticalUiState>,
                           val isEmptyReviews:Boolean,
                           val movieInt: Int,
                           val totalReviews:Int,
                           val totalPages: Boolean
    ) : MovieDetailsItem(MovieDetailsType.RECOMMENDED)

    data class Reviews(val list: ReviewUiState) : MovieDetailsItem(MovieDetailsType.REVIEWS)
}

enum class MovieDetailsType {UPPER, PEOPLE, RECOMMENDED, REVIEWS }
