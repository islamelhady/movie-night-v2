package com.elhady.movies.feature.details.presentation.tvdetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.tvshow.Season
import com.elhady.movies.feature.details.presentation.seasondetails.SeasonHorizontalUiState
import javax.inject.Inject

class TvDetailsSeasonUiMapper @Inject constructor() :
    Mapper<Season, SeasonHorizontalUiState> {
    override fun map(input: Season): SeasonHorizontalUiState {
        return SeasonHorizontalUiState(
            id = input.id,
            imageUrl = input.imageUrl,
            title = input.title,
            description = input.description,
            year = extractYearFromDate(input.year),
            countEpisode = input.countEpisode,
            seasonNumber = input.seasonNumber
        )

    }

    private fun extractYearFromDate(year: String): String {
        val parts = year.split("-")
        return parts[0]
    }

}
