package com.elhady.local.mappers.movies

import com.elhady.local.Constants
import com.elhady.local.database.entity.movies.PopularMovieLocalDto
import com.elhady.remote.response.dto.MovieDto
import com.elhady.remote.response.genre.GenreDto
import javax.inject.Inject

class PopularMovieMapper @Inject constructor()  {
    fun map(movie: MovieDto, genreList: List<GenreDto>): PopularMovieLocalDto {
        return PopularMovieLocalDto(
            id = movie.id ?: 0,
            title = movie.title ?: "",
            movieRate = movie.voteAverage ?: 0.0,
            imageUrl = (Constants.IMAGE_PATH + movie.backdropPath),
            genres = getGenreNames(movie.genreIds, genreList)
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