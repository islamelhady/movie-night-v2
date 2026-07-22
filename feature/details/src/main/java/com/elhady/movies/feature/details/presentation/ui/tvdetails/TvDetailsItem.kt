package com.elhady.movies.feature.details.presentation.ui.tvdetails

import com.elhady.movies.core.common.presentation.model.MediaVerticalUIState
import com.elhady.movies.core.common.presentation.model.PeopleUIState
import com.elhady.movies.core.common.presentation.model.SeasonHorizontalUIState
import com.elhady.movies.feature.details.presentation.tvdetails.TvDetailsUiState
import com.elhady.movies.feature.review.presentation.CommentUIState

sealed class TvDetailsItem(val type: TvDetailsType) {
    data class Upper(val upperUiState: TvDetailsUiState.Info) : TvDetailsItem(TvDetailsType.UPPER)
    data class People(val people: List<PeopleUIState>, val isSeasonNotEmpty: Boolean) : TvDetailsItem(
        TvDetailsType.PEOPLE
    )
    data class Season(val season: SeasonHorizontalUIState) :
        TvDetailsItem(TvDetailsType.Seasons)

    data class Recommended(val recommended: List<MediaVerticalUIState>, val isCommentNotEmpty: Boolean) :
        TvDetailsItem(TvDetailsType.RECOMMENDED)

    data class Review(val review: CommentUIState) : TvDetailsItem(TvDetailsType.REVIEWS)
}

enum class TvDetailsType { UPPER, PEOPLE, Seasons, RECOMMENDED, REVIEWS }
