package com.elhady.movies.feature.details.presentation.ui.tvdetails

import com.elhady.movies.feature.details.presentation.episodedetails.CommentUIState
import com.elhady.movies.core.ui.state.MediaVerticalUiState
import com.elhady.movies.core.ui.state.PeopleUiState
import com.elhady.movies.feature.details.presentation.seasondetails.SeasonHorizontalUIState
import com.elhady.movies.feature.details.presentation.tvdetails.TvDetailsUiState

sealed class TvDetailsItem(val type: TvDetailsType) {
    data class Upper(val upperUiState: TvDetailsUiState.Info) : TvDetailsItem(TvDetailsType.UPPER)
    data class People(val people: List<PeopleUiState>, val isSeasonNotEmpty: Boolean) : TvDetailsItem(
        TvDetailsType.PEOPLE
    )
    data class Season(val season: SeasonHorizontalUIState) :
        TvDetailsItem(TvDetailsType.Seasons)

    data class Recommended(val recommended: List<MediaVerticalUiState>, val isCommentNotEmpty: Boolean) :
        TvDetailsItem(TvDetailsType.RECOMMENDED)

    data class Review(val review: CommentUIState) : TvDetailsItem(TvDetailsType.REVIEWS)
}

enum class TvDetailsType { UPPER, PEOPLE, Seasons, RECOMMENDED, REVIEWS }
