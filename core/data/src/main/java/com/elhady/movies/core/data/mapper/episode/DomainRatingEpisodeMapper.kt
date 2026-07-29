package com.elhady.movies.core.data.mapper.episode

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.tvshow.RatingEpisodeDetailsDto
import com.elhady.movies.core.domain.model.tvshow.RatingEpisodeDetailsStatusEntity
import javax.inject.Inject

class DomainRatingEpisodeMapper @Inject constructor() :
    Mapper<RatingEpisodeDetailsDto, RatingEpisodeDetailsStatusEntity> {

    override fun map(input: RatingEpisodeDetailsDto): RatingEpisodeDetailsStatusEntity {
        return RatingEpisodeDetailsStatusEntity(
            statusCode = input.statusCode ?: 0,
            statusMessage = input.statusMessage ?: "",
            success = input.success ?: false
        )
    }
}
