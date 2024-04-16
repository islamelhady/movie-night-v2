package com.elhady.movies.core.data.repository.mappers.domain.episode

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.remote.response.dto.episode_details.EpisodeDetailsCastRemoteDto
import com.elhady.movies.core.data.repository.Constants.IMAGE_BASE_PATH
import com.elhady.movies.core.domain.entities.PeopleEntity
import javax.inject.Inject

class DomainCastMapper @Inject constructor() :
    Mapper<EpisodeDetailsCastRemoteDto, List<PeopleEntity>> {
    override fun map(input: EpisodeDetailsCastRemoteDto): List<PeopleEntity> {
        return mapCastToEntity(input.cast)
    }


    private fun mapCastToEntity(castRemoteDto: List<EpisodeDetailsCastRemoteDto.CastRemoteDto>?)
            : List<PeopleEntity> {
        return castRemoteDto?.map { actor ->
            PeopleEntity(
                id = actor.id ?: 0,
                name = actor.name ?: "",
                imageUrl = (IMAGE_BASE_PATH + actor.profilePath) ?: ""
            )
        } ?: emptyList()
    }
}