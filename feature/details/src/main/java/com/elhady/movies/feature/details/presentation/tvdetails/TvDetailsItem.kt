package com.elhady.movies.feature.details.presentation.tvdetails

import com.elhady.movies.feature.details.presentation.episodedetails.CommentUiState
import com.elhady.movies.core.ui.state.MediaVerticalUiState
import com.elhady.movies.core.ui.state.PeopleUiState
import com.elhady.movies.feature.details.presentation.seasondetails.SeasonHorizontalUiState
import com.elhady.movies.feature.details.presentation.tvdetails.state.InfoUIState

sealed class TvDetailsItem(val type: TvDetailsType) {
    data class Info(val info: InfoUIState.Info) : TvDetailsItem(TvDetailsType.INFO)
    data class People(val people: List<PeopleUiState>, val isSeasonNotEmpty: Boolean) : TvDetailsItem(
        TvDetailsType.PEOPLE
    )
    data class Season(val season: SeasonHorizontalUiState) :
        TvDetailsItem(TvDetailsType.Seasons)

    data class Recommended(val recommended: List<MediaVerticalUiState>, val isCommentNotEmpty: Boolean) :
        TvDetailsItem(TvDetailsType.RECOMMENDED)

    data class Review(val review: CommentUiState) : TvDetailsItem(TvDetailsType.REVIEWS)
}

enum class TvDetailsType { INFO, PEOPLE, Seasons, RECOMMENDED, REVIEWS }
