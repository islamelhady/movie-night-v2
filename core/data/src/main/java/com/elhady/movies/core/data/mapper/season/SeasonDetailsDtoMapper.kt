package com.elhady.movies.core.data.mapper.season

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.tvshow.EpisodeDto
import com.elhady.movies.core.network.dto.tvshow.SeasonDetailsDto
import com.elhady.movies.core.domain.model.tvshow.Episode
import com.elhady.movies.core.domain.model.tvshow.SeasonDetails
import javax.inject.Inject

class SeasonDetailsDtoMapper @Inject constructor() :
    Mapper<SeasonDetailsDto, SeasonDetails> {

    override fun map(input: SeasonDetailsDto): SeasonDetails {
        return SeasonDetails(
            id = input.id ?: 0,
            name = input.name ?: "",
            overview = input.overview ?: "",
            episodes = mapEpisodes(input.episodes ?: emptyList())
        )
    }

    private fun mapEpisodes(input: List<EpisodeDto>): List<Episode>{
        return input.map {
            Episode(
                id = it.id ?: 0,
                imageUrl = BuildConfig.IMAGE_BASE_PATH + it.stillPath ,
                title = it.name ?: "",
                overview = it.overview ?: "",
                timeEpisode = it.runtime ?: 0,
                rate = it.voteAverage ?: 0.0,
                episodeNumber = it.episodeNumber ?: 0
            )
        }
    }
}
