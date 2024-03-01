package com.elhady.viewmodel.seriesDetails.seriesUiMapper

import com.elhady.mapper.Mapper
import com.elhady.viewmodel.seriesDetails.SeasonUiState
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