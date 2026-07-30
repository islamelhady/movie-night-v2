package com.elhady.movies.feature.details.presentation.seasondetails

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.tvshow.Episode
import com.elhady.movies.core.domain.model.tvshow.SeasonDetails
import com.elhady.movies.feature.details.presentation.episodedetails.EpisodeHorizontalUiState
import javax.inject.Inject

class SeasonDetailsUiMapper @Inject constructor():
    Mapper<SeasonDetails, SeasonDetailsUiState> {

    override fun map(input: SeasonDetails): SeasonDetailsUiState {
        return SeasonDetailsUiState(
            name = input.name,
            overview = input.overview,
            id = input.id,
            episodes = mapEpisodes(input.episodes),
            onErrors = emptyList(),
            isLoading = false
        )
    }

    private fun mapEpisodes(input: List<Episode>): List<EpisodeHorizontalUiState>{
        return input.map {
            EpisodeHorizontalUiState(
                id = it.id,
                imageUrl = it.imageUrl,
                title = it.title,
                timeEpisode = it.timeEpisode,
                rate = it.rate,
                description = it.overview,
                numberEpisode = it.episodeNumber
            )
        }
    }
}
