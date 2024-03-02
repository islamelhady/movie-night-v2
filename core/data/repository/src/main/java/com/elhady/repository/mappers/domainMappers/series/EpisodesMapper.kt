package com.elhady.movies.domain.mappers.series

import com.elhady.movies.data.remote.response.episode.EpisodeDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class EpisodesMapper @Inject constructor(): Mapper<EpisodeDto, Episode> {
    override fun map(input: EpisodeDto): Episode {
        return Episode(
            episodeDate = input.airDate ?: "",
            episodeDescription = input.overview ?: "",
            episodeDuration = input.runtime ?: 0,
            episodeId = input.id ?: 0,
            episodeImageUrl = (Constants.IMAGE_PATH + input.stillPath),
            episodeName = input.name ?: "",
            episodeNumber = input.episodeNumber ?: 1,
            episodeRate = input.voteAverage ?: 0.0,
            episodeTotalReviews = input.voteCount.toString().take(3)
        )
    }
}