package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.domain.mappers.GenreMapper
import com.elhady.movies.domain.models.Genre
import com.elhady.movies.utilities.Constants
import com.elhady.movies.utilities.Constants.FIRST_CATEGORY_NAME
import javax.inject.Inject

class GetGenreListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository,
    private val genreMapper: GenreMapper
) {
    suspend operator fun invoke(type: MediaType): List<Genre> {
        val genre = when (type) {
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

        return setGenre(genre)
    }

    private fun setGenre(genre: List<Genre>): List<Genre> {
        val genresList = mutableListOf<Genre>()
        genresList.add(0, Genre(Constants.FIRST_CATEGORY_ID, FIRST_CATEGORY_NAME))
        genresList.addAll(genre)
        return genresList.toList()

    }


}