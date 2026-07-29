package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.tvshow.TvDetailsRemoteDto
import com.elhady.movies.core.domain.model.tvshow.SeasonEntity
import javax.inject.Inject

class DomainTvDetailsSeasonMapper @Inject constructor() :
    Mapper<TvDetailsRemoteDto, List<SeasonEntity>> {
    override fun map(input: TvDetailsRemoteDto): List<SeasonEntity> {
        return input.seasons?.map { season ->
             SeasonEntity(
                 id = season?.id ?: 0,
                 imageUrl = BuildConfig.IMAGE_BASE_PATH + season?.posterPath,
                 title = season?.name ?: "",
                 description = season?.overview ?: "",
                 year = season?.airDate ?: "",
                 countEpisode = season?.episodeCount ?: 0,
                 seasonNumber = season?.seasonNumber ?: 0
            )
        }?: emptyList()
    }
}
