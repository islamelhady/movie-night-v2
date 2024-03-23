package com.elhady.movies.core.data.repository.mappers.domain

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.remote.response.dto.season_details.EpisodeDto
import com.elhady.movies.core.data.remote.response.dto.season_details.SeasonDetailsDto
import com.elhady.movies.core.data.repository.Constants.IMAGE_BASE_PATH
import com.elhady.movies.core.domain.entities.season_details.EpisodeEntity
import com.elhady.movies.core.domain.entities.season_details.SeasonDetailsEntity
import javax.inject.Inject

class DomainSeasonDetailsMapper @Inject constructor() :
    Mapper<SeasonDetailsDto, SeasonDetailsEntity> {

    override fun map(input: SeasonDetailsDto): SeasonDetailsEntity {
        return SeasonDetailsEntity(
            id = input.id ?: 0,
            name = input.name ?: "",
            overview = input.overview ?: "",
            episodes = mapEpisodes(input.episodes ?: emptyList())
        )
    }

    private fun mapEpisodes(input: List<EpisodeDto>): List<EpisodeEntity>{
        return input.map {
            EpisodeEntity(
                id = it.id ?: 0,
                imageUrl = IMAGE_BASE_PATH + it.stillPath ,
                title = it.name ?: "",
                overview = it.overview ?: "",
                timeEpisode = it.runtime ?: 0,
                rate = it.voteAverage ?: 0.0,
                episodeNumber = it.episodeNumber ?: 0
            )
        }
    }
}