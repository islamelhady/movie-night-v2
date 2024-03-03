package com.elhady.repository.mappers.cash

import com.elhady.local.Constants
import com.elhady.local.database.dto.movies.PopularMovieLocalDto
import com.elhady.remote.response.dto.MovieRemoteDto
import com.elhady.remote.response.genre.GenreMovieRemoteDto
import com.elhady.repository.mappers.Mapper
import javax.inject.Inject

class LocalPopularMovieMapper @Inject constructor(){

    fun map(movie: MovieRemoteDto, genreList: List<GenreMovieRemoteDto>): PopularMovieLocalDto {
        return PopularMovieLocalDto(
            id = movie.id ?: 0,
            title = movie.title ?: "",
            movieRate = movie.voteAverage ?: 0.0,
            imageUrl = (Constants.IMAGE_PATH + movie.posterPath),
            genres = getGenreNames(movie.genreIds, genreList)
        )
    }

    private fun getGenreNames(
        genreIds: List<Int>?,
        genreItems: List<GenreMovieRemoteDto>
    ): List<String> {
        val genres = mutableListOf<String>()
        genreIds?.forEach { movieGenreID ->
            genres.add(genreItems.find { it.id == movieGenreID }?.name ?: "")
        }
        return genres
    }
}