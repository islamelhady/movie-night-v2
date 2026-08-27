package com.elhady.movies.feature.details.presentation.tvdetails

import com.elhady.movies.feature.details.presentation.episodedetails.CommentUiState
import com.elhady.movies.core.ui.state.MediaVerticalUiState
import com.elhady.movies.core.ui.state.PeopleUiState
import com.elhady.movies.feature.details.presentation.seasondetails.SeasonHorizontalUiState
import com.elhady.movies.feature.details.presentation.tvdetails.state.InfoUIState

sealed interface TvDetailsItem {
    data class Info(val info: InfoUIState.Info) : TvDetailsItem
    data class People(val people: List<PeopleUiState>, val isSeasonNotEmpty: Boolean) : TvDetailsItem

    data class Season(val season: SeasonHorizontalUiState) : TvDetailsItem

    data class Recommended(val recommended: List<MediaVerticalUiState>, val isCommentNotEmpty: Boolean) : TvDetailsItem

    data class Review(val review: CommentUiState) : TvDetailsItem
}
