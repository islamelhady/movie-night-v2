package com.elhady.local.mappers.movies

import com.elhady.local.Constants
import com.elhady.local.database.entity.movies.UpcomingMovieEntity
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.dto.MovieDto
import javax.inject.Inject

class UpcomingMovieMapper @Inject constructor(): Mapper<MovieDto, UpcomingMovieEntity> {
    override fun map(input: MovieDto): UpcomingMovieEntity {
        return UpcomingMovieEntity(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}