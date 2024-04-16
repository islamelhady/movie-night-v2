package com.elhady.movies.presentation.viewmodel.episodedetails

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.EpisodeDetailsEntity
import javax.inject.Inject

class EpisodeDetailsUiMapper  @Inject constructor() :
    Mapper<EpisodeDetailsEntity, EpisodeDetailsUiState> {
    override fun map(input: EpisodeDetailsEntity): EpisodeDetailsUiState {
        return EpisodeDetailsUiState(
            imageUrl = input.imageUrl,
            episodeNumber = input.episodeNumber,
            episodeName = input.episodeName,
            episodeRate = input.episodeRate,
            episodeOverview = input.overview,
            seasonNumber = input.seasonNumber,
            voteAverage = input.voteAverage
        )
    }
}