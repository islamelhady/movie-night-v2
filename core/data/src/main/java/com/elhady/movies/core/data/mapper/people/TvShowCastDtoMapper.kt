package com.elhady.movies.core.data.mapper.people

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.people.TvShowCastDto
import com.elhady.movies.core.domain.model.tvshow.TvShow
import javax.inject.Inject

class TvShowCastDtoMapper @Inject constructor() : Mapper<TvShowCastDto, TvShow> {

    override fun map(input: List<TvShowCastDto>): List<TvShow> {
        return input.map(::map)
    }

    override fun map(input: TvShowCastDto): TvShow {
        return TvShow(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            rate = (input.voteAverage as? Double)?.times(0.5) ?: 0.0
        )
    }
}
