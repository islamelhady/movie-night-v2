package com.elhady.movies.core.data.mapper.episode

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.network.dto.tvshow.EpisodeDetailsDto
import com.elhady.movies.core.domain.model.tvshow.EpisodeDetails
import javax.inject.Inject

class EpisodeDetailsDtoMapper @Inject constructor() :
    Mapper<EpisodeDetailsDto, EpisodeDetails> {
    override fun map(input: EpisodeDetailsDto): EpisodeDetails {
        return EpisodeDetails(
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
