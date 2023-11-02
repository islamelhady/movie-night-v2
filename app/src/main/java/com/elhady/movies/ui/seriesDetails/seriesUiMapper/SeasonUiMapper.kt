package com.elhady.movies.ui.seriesDetails.seriesUiMapper

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Season
import com.elhady.movies.ui.seriesDetails.seriesUiState.SeasonUiState
import javax.inject.Inject

class SeasonUiMapper @Inject constructor(): Mapper<Season, SeasonUiState> {
    override fun map(input: Season): SeasonUiState {
        return SeasonUiState(
            seasonYear = input.seasonYear,
            seasonPoster = input.seasonPoster,
            seasonNumber = input.seasonNumber,
            seasonName = input.seasonName,
            seasonId = input.seasonId,
            seasonEpisodeCount = input.seasonEpisodeCount,
            seasonOverview = input.seasonOverview
        )
    }
}