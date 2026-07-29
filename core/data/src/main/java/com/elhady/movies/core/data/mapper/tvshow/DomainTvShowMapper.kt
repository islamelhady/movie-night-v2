package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.tvshow.TVShowsDto
import com.elhady.movies.core.domain.model.tvshow.TvShowEntity
import javax.inject.Inject

class DomainTvShowMapper @Inject constructor() : Mapper<TVShowsDto, TvShowEntity> {

    override fun map(input: List<TVShowsDto>): List<TvShowEntity> {
        return input.map(::map)
    }

    override fun map(input: TVShowsDto): TvShowEntity {
        return TvShowEntity(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            rate = input.voteAverage?.times(0.5) ?: 0.0
        )
    }
}
