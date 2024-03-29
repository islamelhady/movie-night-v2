package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.mappers.movie.RatedMoviesMapper
import com.elhady.movies.domain.mappers.series.RatedSeriesMapper
import com.elhady.movies.domain.models.Rated
import com.elhady.movies.utilities.margeTwoList
import javax.inject.Inject

class GetListOfRatedUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val ratedMoviesMapper: RatedMoviesMapper,
    private val seriesRepository: SeriesRepository,
    private val ratedSeriesMapper: RatedSeriesMapper
) {

    suspend operator fun invoke(): List<Rated>{
        return getRatedMovies().margeTwoList(getRatedSeries()).reversed()
    }

    private suspend fun getRatedMovies(): List<Rated> {
        val response = movieRepository.getRatedMovie()
        return response?.let(ratedMoviesMapper::map) ?: throw Throwable("not success")
    }

    private suspend fun getRatedSeries(): List<Rated>{
        val response = seriesRepository.getRatedSeries()
            return response?.let(ratedSeriesMapper::map) ?: throw Throwable("not success")
    }

}