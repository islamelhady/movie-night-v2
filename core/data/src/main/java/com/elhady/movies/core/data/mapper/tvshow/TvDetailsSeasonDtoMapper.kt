package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.network.dto.tvshow.TvDetailsDto
import com.elhady.movies.core.domain.model.tvshow.Season
import javax.inject.Inject

class TvDetailsSeasonDtoMapper @Inject constructor() :
    Mapper<TvDetailsDto, List<Season>> {
    override fun map(input: TvDetailsDto): List<Season> {
        return input.seasons?.map { season ->
             Season(
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
