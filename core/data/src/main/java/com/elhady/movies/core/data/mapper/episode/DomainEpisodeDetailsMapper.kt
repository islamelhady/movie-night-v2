package com.elhady.movies.core.data.mapper.episode

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.model.response.dto.episode_details.EpisodeDetailsRemoteDto
import com.elhady.movies.core.domain.model.EpisodeDetailsEntity
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
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.stillPath,
            episodeRate = input.voteAverage ?: 0.0F
        )
    }
}
