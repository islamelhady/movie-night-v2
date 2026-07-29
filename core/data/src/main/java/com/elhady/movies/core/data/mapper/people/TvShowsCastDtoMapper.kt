package com.elhady.movies.core.data.mapper.people

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.people.TvShowsCastDto
import com.elhady.movies.core.domain.model.tvshow.TvShow
import javax.inject.Inject

class TvShowsCastDtoMapper @Inject constructor() : Mapper<TvShowsCastDto, TvShow> {

    override fun map(input: List<TvShowsCastDto>): List<TvShow> {
        return input.map(::map)
    }

    override fun map(input: TvShowsCastDto): TvShow {
        return TvShow(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            rate = (input.voteAverage as? Double)?.times(0.5) ?: 0.0
        )
    }
}
