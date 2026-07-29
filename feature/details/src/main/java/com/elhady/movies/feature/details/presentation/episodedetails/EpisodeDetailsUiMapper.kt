package com.elhady.movies.feature.details.presentation.episodedetails

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.tvshow.EpisodeDetails
import javax.inject.Inject

class EpisodeDetailsUiMapper  @Inject constructor() :
    Mapper<EpisodeDetails, EpisodeDetailsUiState> {
    override fun map(input: EpisodeDetails): EpisodeDetailsUiState {
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
