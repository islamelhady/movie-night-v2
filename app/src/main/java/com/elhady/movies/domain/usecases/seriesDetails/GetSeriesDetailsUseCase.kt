package com.elhady.movies.domain.usecases.seriesDetails

import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.mappers.actor.ActorDtoMapper
import com.elhady.movies.domain.mappers.movie.MovieDtoMapper
import com.elhady.movies.domain.mappers.series.SeriesDetailsMapper
import com.elhady.movies.domain.mappers.series.SeriesDtoMapper
import com.elhady.movies.domain.models.Actor
import com.elhady.movies.domain.models.Media
import com.elhady.movies.domain.models.SeriesDetails
import javax.inject.Inject

class GetSeriesDetailsUseCase @Inject constructor(
    private val repository: SeriesRepository,
    private val seriesDetailsMapper: SeriesDetailsMapper,
    private val actorDtoMapper: ActorDtoMapper,
    private val seriesDtoMapper: SeriesDtoMapper
) {

    suspend fun getSeriesDetails(seriesId: Int): SeriesDetails {
        val response = repository.getSeriesDetails(seriesId)
        return response?.let {
            seriesDetailsMapper.map(it)
        } ?: throw Throwable("Not success")
    }

    suspend fun getSeriesCast(seriesId: Int): List<Actor> {
        return repository.getSeriesCast(seriesId)?.cast?.map {
            actorDtoMapper.map(it)
        } ?: throw Throwable("not found")
    }

    suspend fun getSimilarSeries(seriesId: Int): List<Media> {
        return repository.getSimilarSeries(seriesId)?.map {
            seriesDtoMapper.map(it)
        } ?: throw Throwable("not success")
    }


}