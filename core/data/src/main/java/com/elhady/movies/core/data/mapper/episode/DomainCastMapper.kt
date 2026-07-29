package com.elhady.movies.core.data.mapper.episode

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.tvshow.EpisodeDetailsCastDto
import com.elhady.movies.core.domain.model.people.PeopleEntity
import javax.inject.Inject

class DomainCastMapper @Inject constructor() :
    Mapper<EpisodeDetailsCastDto, List<PeopleEntity>> {
    override fun map(input: EpisodeDetailsCastDto): List<PeopleEntity> {
        return mapCastToEntity(input.cast)
    }

    private fun mapCastToEntity(castDto: List<EpisodeDetailsCastDto.CastDto>?)
            : List<PeopleEntity> {
        return castDto?.map { actor ->
            PeopleEntity(
                id = actor.id ?: 0,
                name = actor.name ?: "",
                imageUrl = BuildConfig.IMAGE_BASE_PATH + actor.profilePath
            )
        } ?: emptyList()
    }
}
