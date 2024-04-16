package com.elhady.movies.core.data.repository.mappers.domain.episode

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.remote.response.dto.episode_details.EpisodeDetailsRemoteDto
import com.elhady.movies.core.data.repository.Constants.IMAGE_BASE_PATH
import com.elhady.movies.core.domain.entities.EpisodeDetailsEntity
import javax.inject.Inject

class DomainEpisodeDetailsMapper @Inject constructor() :
    Mapper<EpisodeDetailsRemoteDto, EpisodeDetailsEntity> {
    override fun map(input: EpisodeDetailsRemoteDto): EpisodeDetailsEntity {
        return EpisodeDetailsEntity(
            id = input.id ?: 0,
            overview = input.overview ?: "",
            productionCode = input.productionCode ?: "",
            seasonNumber = input.seasonNumber ?: 0,
            episodeNumber = input.episodeNumber ?: 0,
            episodeName = input.name ?: "",
            voteAverage = input.voteAverage ?: 0f,
            imageUrl = IMAGE_BASE_PATH + input.stillPath,
            episodeRate = input.voteAverage ?: 0.0F
        )
    }

}