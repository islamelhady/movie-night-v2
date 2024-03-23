package com.elhady.movies.presentation.ui.tvdetails

import com.elhady.movies.presentation.viewmodel.common.model.CommentUIState
import com.elhady.movies.presentation.viewmodel.common.model.MediaVerticalUIState
import com.elhady.movies.presentation.viewmodel.common.model.PeopleUIState
import com.elhady.movies.presentation.viewmodel.common.model.SeasonHorizontalUIState
import com.elhady.movies.presentation.viewmodel.tvdetails.TvDetailsUiState

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
