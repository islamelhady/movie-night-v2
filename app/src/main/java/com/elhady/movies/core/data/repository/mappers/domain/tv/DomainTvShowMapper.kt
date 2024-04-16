package com.elhady.movies.core.data.repository.mappers.domain.tv

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.remote.response.dto.TVShowsRemoteDto
import com.elhady.movies.core.data.repository.Constants.IMAGE_BASE_PATH
import com.elhady.movies.core.domain.entities.TvShowEntity
import javax.inject.Inject

class DomainTvShowMapper @Inject constructor() : Mapper<TVShowsRemoteDto, TvShowEntity> {

    override fun map(input: List<TVShowsRemoteDto>): List<TvShowEntity> {
        return input.map(::map)
    }

    override fun map(input: TVShowsRemoteDto): TvShowEntity {
        return TvShowEntity(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = (IMAGE_BASE_PATH + input.posterPath),
            rate = input.voteAverage?.times(0.5) ?: 0.0
        )
    }
}