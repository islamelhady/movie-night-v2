package com.elhady.viewmodel.seriesDetails.episodes

import com.elhady.mapper.Mapper
import javax.inject.Inject

class EpisodeUiMapper @Inject constructor(): Mapper<Episode, SeasonEpisodesUiState> {
    override fun map(input: Episode): SeasonEpisodesUiState {
        return SeasonEpisodesUiState(
            episodeDate = input.episodeDate,
            episodeTotalReviews = input.episodeTotalReviews,
            episodeRate = input.episodeRate,
            episodeNumber = input.episodeNumber,
            episodeName = input.episodeName,
            episodeImageUrl = input.episodeImageUrl,
            episodeId = input.episodeId,
            episodeDuration = input.episodeDuration,
            episodeDescription = input.episodeDescription
        )
    }
}