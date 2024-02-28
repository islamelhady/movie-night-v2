package com.elhady.usecase

import com.elhady.entities.RatedEntity
import com.elhady.usecase.repository.MovieRepository
import com.elhady.usecase.repository.SeriesRepository
import javax.inject.Inject

class GetListOfRatedUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val ratedMoviesMapper: RatedMoviesMapper,
    private val seriesRepository: SeriesRepository,
    private val ratedSeriesMapper: RatedSeriesMapper
) {

    suspend operator fun invoke(): List<RatedEntity>{
        return getRatedMovies().margeTwoList(getRatedSeries()).reversed()
    }

    private suspend fun getRatedMovies(): List<RatedEntity> {
        val response = movieRepository.getRatedMovie()
        return response?.let(ratedMoviesMapper::map) ?: throw Throwable("not success")
    }

    private suspend fun getRatedSeries(): List<RatedEntity>{
        val response = seriesRepository.getRatedSeries()
            return response?.let(ratedSeriesMapper::map) ?: throw Throwable("not success")
    }

    companion object {
        fun <T> List<T>.margeTwoList(secondList: List<T>): List<T>{
            return this.plus(secondList)
        }
    }
}