package com.elhady.usecase.seriesDetails

import com.elhady.entities.EpisodeEntity
import com.elhady.usecase.repository.SeriesRepository
import javax.inject.Inject

class GetSeasonsEpisodesUseCase @Inject constructor(
    private val repository: SeriesRepository,
    private val episodesMapper: EpisodesMapper
) {
    suspend operator fun invoke(seriesId: Int, seasonNumber: Int): List<EpisodeEntity> {
        val response = repository.getSeasonDetails(seriesId = seriesId, seasonNumber = seasonNumber)
        return response?.let (episodesMapper::map)
         ?: throw Throwable("not success")
    }
}