package com.elhady.movies.core.data.repository.mappers.domain.episode

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.model.response.dto.episode_details.RatingEpisodeDetailsRemoteDto
import com.elhady.movies.core.common.domain.entities.RatingEpisodeDetailsStatusEntity
import javax.inject.Inject

class DomainRatingEpisodeMapper @Inject constructor() :
    Mapper<RatingEpisodeDetailsRemoteDto, RatingEpisodeDetailsStatusEntity> {

    override fun map(input: RatingEpisodeDetailsRemoteDto): RatingEpisodeDetailsStatusEntity {
        return RatingEpisodeDetailsStatusEntity(
            statusCode = input.statusCode ?: 0,
            statusMessage = input.statusMessage ?: "",
            success = input.success ?: false
        )
    }
}
