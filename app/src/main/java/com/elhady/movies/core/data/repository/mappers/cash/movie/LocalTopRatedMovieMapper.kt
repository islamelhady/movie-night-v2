package com.elhady.movies.core.data.repository.mappers.cash.movie

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.local.database.dto.movie.TopRatedMovieLocalDto
import com.elhady.movies.core.data.remote.response.dto.MovieRemoteDto
import com.elhady.movies.core.data.repository.Constants.IMAGE_BASE_PATH
import javax.inject.Inject

class LocalTopRatedMovieMapper @Inject constructor():
    Mapper<MovieRemoteDto, TopRatedMovieLocalDto> {
    override fun map(input: MovieRemoteDto): TopRatedMovieLocalDto {
        return TopRatedMovieLocalDto(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = IMAGE_BASE_PATH + input.posterPath,
            rate = input.voteAverage ?: 0.0
        )
    }
}