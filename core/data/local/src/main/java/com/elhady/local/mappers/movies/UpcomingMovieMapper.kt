package com.elhady.local.mappers.movies

import com.elhady.local.Constants
import com.elhady.local.database.entity.movies.UpcomingMovieLocalDto
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.dto.MovieDto
import javax.inject.Inject

class UpcomingMovieMapper @Inject constructor(): Mapper<MovieDto, UpcomingMovieLocalDto> {
    override fun map(input: MovieDto): UpcomingMovieLocalDto {
        return UpcomingMovieLocalDto(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}