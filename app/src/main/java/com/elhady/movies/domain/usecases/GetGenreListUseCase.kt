package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.domain.mappers.GenreMapper
import com.elhady.movies.domain.models.Genre
import javax.inject.Inject

class GetGenreListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository,
    private val genreMapper: GenreMapper
) {
    suspend operator fun invoke(type: MediaType): List<Genre> {
        return when (type) {
            MediaType.MOVIES -> {
                movieRepository.getGenreMovies()?.map {
                    genreMapper.map(it)

                } ?: throw Throwable("not success")
            }

            MediaType.SERIES -> {
                seriesRepository.getGenreSeries()?.map {
                    genreMapper.map(it)
                } ?: throw Throwable("not success")
            }
        }

    }


}