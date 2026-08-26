package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.tvshow.TvShowDto
import com.elhady.movies.core.domain.model.tvshow.TvShows
import javax.inject.Inject

class AiringTodayTvShowDtoMapper @Inject constructor() :
    Mapper<TvShowDto, TvShows> {

    override fun map(input: TvShowDto): TvShows {
        return TvShows(
            id = input.id ?: 0,
            title = input.name ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath ,
            genreEntities = emptyList(),
            rate = input.voteAverage ?: 0.0,
            year = input.firstAirDate ?: "Unknown"
        )
    }
}
