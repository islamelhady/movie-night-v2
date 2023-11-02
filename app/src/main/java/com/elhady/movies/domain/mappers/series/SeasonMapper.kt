package com.elhady.movies.domain.mappers.series

import com.elhady.movies.data.remote.response.series.SeasonDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Season
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class SeasonMapper @Inject constructor() : Mapper<SeasonDto, Season> {
    override fun map(input: SeasonDto): Season {
        return Season(
            seasonOverview = input.overview ?: "",
            seasonId = input.id ?: 0,
            seasonEpisodeCount = input.episodeCount ?: 0,
            seasonName = input.name ?: "",
            seasonNumber = input.seasonNumber ?: 0,
            seasonPoster = (Constants.IMAGE_PATH + input.posterPath),
            seasonYear = input.airDate ?: "",
        )
    }

}
