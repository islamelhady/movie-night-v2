package com.elhady.movies.ui.tvShowDetails.tvShowUiMapper

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.TVShowDetails
import com.elhady.movies.ui.tvShowDetails.TVShowDetailsResultUiState
import javax.inject.Inject

class TvShowDetailsUiMapper @Inject constructor(): Mapper<TVShowDetails, TVShowDetailsResultUiState>{
    override fun map(input: TVShowDetails): TVShowDetailsResultUiState {
        return TVShowDetailsResultUiState(
            tvShowId = input.tvShowId,
            tvShowVoteAverage = input.tvShowVoteAverage,
            tvShowReview = input.tvShowReview,
            tvShowSeasonsNumber = input.tvShowSeasonsNumber,
            tvShowReleaseDate = input.tvShowReleaseDate,
            tvShowOverview = input.tvShowOverview,
            tvShowName = input.tvShowName,
            tvShowImage = input.tvShowImage,
            tvShowGenres = input.tvShowGenres
        )
    }
}