package com.elhady.movies.domain.mappers

import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.data.remote.response.genre.GenreDto
import com.elhady.movies.domain.models.PopularMovie
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class PopularMovieMapper @Inject constructor()  {
    fun map(movie: MovieDto, genreList: List<GenreDto>): PopularMovie {
        return PopularMovie(
            movieId = movie.id ?: 0,
            title = movie.title ?: "",
            movieRate = movie.voteAverage ?: 0.0,
            imageUrl = (Constants.IMAGE_PATH + movie.backdropPath),
            genre = getGenreNames(movie.genreIds, genreList)
        )
    }

    private fun getGenreNames(genreIds: List<Int>?, genreList: List<GenreDto>): List<String> {
        val genres = mutableListOf<String>()
        genreIds?.forEach { movieGenreID ->
            genres.add(genreList.find { it.id == movieGenreID }?.name ?: "")
        }
        return genres
    }
}