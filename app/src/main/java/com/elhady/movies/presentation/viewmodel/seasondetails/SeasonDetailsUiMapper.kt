package com.elhady.movies.presentation.viewmodel.seasondetails

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.seasondetails.EpisodeEntity
import com.elhady.movies.core.domain.entities.seasondetails.SeasonDetailsEntity
import com.elhady.movies.presentation.viewmodel.common.model.EpisodeHorizontalUIState
import javax.inject.Inject

class SeasonDetailsUiMapper @Inject constructor():
    Mapper<SeasonDetailsEntity, SeasonDetailsUiState> {

    override fun map(input: SeasonDetailsEntity): SeasonDetailsUiState {
        return SeasonDetailsUiState(
            name = input.name,
            overview = input.overview,
            id = input.id,
            episodes = mapEpisodes(input.episodes),
            onErrors = emptyList(),
            isLoading = false
        )
    }

    private fun mapEpisodes(input: List<EpisodeEntity>): List<EpisodeHorizontalUIState>{
        return input.map {
            EpisodeHorizontalUIState(
                id = it.id,
                imageUrl = it.imageUrl,
                title = it.title,
                timeEpisode = it.timeEpisode,
                rate = it.rate,
                Description = it.overview,
                numberEpisode = it.episodeNumber
            )
        }
    }
}