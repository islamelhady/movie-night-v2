package com.elhady.movies.presentation.viewmodel.tvdetails.mappers

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.SeasonEntity
import com.elhady.movies.presentation.viewmodel.common.model.SeasonHorizontalUIState
import com.elhady.movies.presentation.viewmodel.tvdetails.TvDetailsUiState
import javax.inject.Inject

class TvDetailsSeasonUiMapper @Inject constructor() : Mapper<List<SeasonEntity>, TvDetailsUiState> {
    override fun map(input: List<SeasonEntity>): TvDetailsUiState {
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