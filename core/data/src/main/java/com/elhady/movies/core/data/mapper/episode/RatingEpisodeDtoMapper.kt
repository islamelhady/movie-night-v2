package com.elhady.movies.core.data.mapper.episode

import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.network.dto.tvshow.RatingEpisodeDetailsDto
import com.elhady.movies.core.domain.model.tvshow.RatingEpisodeDetailsStatus
import javax.inject.Inject

class RatingEpisodeDtoMapper @Inject constructor() :
    Mapper<RatingEpisodeDetailsDto, RatingEpisodeDetailsStatus> {

    override fun map(input: RatingEpisodeDetailsDto): RatingEpisodeDetailsStatus {
        return RatingEpisodeDetailsStatus(
            statusCode = input.statusCode ?: 0,
            statusMessage = input.statusMessage ?: "",
            success = input.success ?: false
        )
    }
}
