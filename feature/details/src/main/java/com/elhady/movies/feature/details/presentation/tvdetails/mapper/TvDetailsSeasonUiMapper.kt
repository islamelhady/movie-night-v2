package com.elhady.movies.feature.details.presentation.tvdetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.tvshow.Season
import com.elhady.movies.feature.details.presentation.seasondetails.SeasonHorizontalUIState
import com.elhady.movies.feature.details.presentation.tvdetails.TvDetailsUiState
import javax.inject.Inject

class TvDetailsSeasonUiMapper @Inject constructor() : Mapper<List<Season>, TvDetailsUiState> {
    override fun map(input: List<Season>): TvDetailsUiState {
        return TvDetailsUiState(
            seasons = input.map { season ->
                SeasonHorizontalUIState(
                    id = season.id,
                    imageUrl = season.imageUrl,
                    title = season.title,
                    description = season.description,
                    year = extractYearFromDate(season.year),
                    countEpisode = season.countEpisode,
                    seasonNumber =  season.seasonNumber
                )
            }
        )
    }

    private fun extractYearFromDate(year: String): String {
        val parts = year.split("-")
        return parts[0]
    }

}
