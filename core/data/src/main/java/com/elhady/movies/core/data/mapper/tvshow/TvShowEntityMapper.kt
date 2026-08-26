package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.tvshow.TvShowDto
import com.elhady.movies.core.domain.model.tvshow.TvShow
import javax.inject.Inject

class TvShowEntityMapper @Inject constructor() : Mapper<TvShowDto, TvShow> {

    override fun map(input: List<TvShowDto>): List<TvShow> {
        return input.map(::map)
    }

    override fun map(input: TvShowDto): TvShow {
        return TvShow(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            rate = input.voteAverage?.times(0.5) ?: 0.0
        )
    }
}
