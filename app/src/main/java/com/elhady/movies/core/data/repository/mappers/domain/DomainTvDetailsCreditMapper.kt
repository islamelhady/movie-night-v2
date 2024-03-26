package com.elhady.movies.core.data.repository.mappers.domain

import com.elhady.movies.BuildConfig
import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.remote.response.dto.TvDetailsCreditRemoteDto
import com.elhady.movies.core.domain.entities.PeopleEntity
import javax.inject.Inject

class DomainTvDetailsCreditMapper @Inject constructor() :
    Mapper<TvDetailsCreditRemoteDto, List<PeopleEntity>> {
    override fun map(input: TvDetailsCreditRemoteDto): List<PeopleEntity> {
        return mapCastToEntity(input.cast)
    }

    private fun mapCastToEntity(castRemoteDto: List<TvDetailsCreditRemoteDto.Cast?>?)
            : List<PeopleEntity> {
        return castRemoteDto?.map { actor ->
            PeopleEntity(
                id = actor?.id ?: 0,
                name = actor?.name ?: "",
                imageUrl = (BuildConfig.IMAGE_BASE_PATH + actor?.profilePath) ?: ""
            )
        } ?: emptyList()
    }

}
