package com.elhady.movies.core.data.repository.mappers.domain

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.remote.response.dto.TvDetailsRemoteDto
import com.elhady.movies.core.data.repository.Constants.IMAGE_BASE_PATH
import com.elhady.movies.core.domain.entities.SeasonEntity
import javax.inject.Inject

class DomainTvDetailsSeasonMapper @Inject constructor() :
    Mapper<TvDetailsRemoteDto, List<SeasonEntity>> {
    override fun map(input: TvDetailsRemoteDto): List<SeasonEntity> {
        return input.seasons?.map { season ->
             SeasonEntity(
                 id = season?.id ?: 0,
                 imageUrl = IMAGE_BASE_PATH + season?.posterPath,
                 title = season?.name ?: "",
                 description = season?.overview ?: "",
                 year = season?.airDate ?: "",
                 countEpisode = season?.episodeCount ?: 0,
                 seasonNumber = season?.seasonNumber ?: 0
            )
        }?: emptyList()
    }
}