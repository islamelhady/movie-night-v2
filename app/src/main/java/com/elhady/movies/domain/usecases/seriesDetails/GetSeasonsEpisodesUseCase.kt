package com.elhady.movies.domain.usecases.seriesDetails

import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.mappers.series.Episode
import com.elhady.movies.domain.mappers.series.EpisodesMapper
import javax.inject.Inject

class GetSeasonsEpisodesUseCase @Inject constructor(
    private val repository: SeriesRepository,
    private val episodesMapper: EpisodesMapper
) {
    suspend operator fun invoke(seriesId: Int, seasonNumber: Int): List<Episode> {
        val response = repository.getSeasonDetails(seriesId = seriesId, seasonNumber = seasonNumber)
        return response?.let (episodesMapper::map)
         ?: throw Throwable("not success")
    }
}