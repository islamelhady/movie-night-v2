package com.elhady.repository.mappers.cash.movies

import com.elhady.local.Constants
import com.elhady.local.database.dto.movies.UpcomingMovieLocalDto
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.dto.MovieRemoteDto
import javax.inject.Inject

class LocalUpcomingMovieMapper @Inject constructor(): Mapper<MovieRemoteDto, UpcomingMovieLocalDto> {
    override fun map(input: MovieRemoteDto): UpcomingMovieLocalDto {
        return UpcomingMovieLocalDto(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}