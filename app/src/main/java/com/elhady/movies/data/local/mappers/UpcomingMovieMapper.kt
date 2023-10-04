package com.elhady.movies.data.local.mappers

import com.elhady.movies.data.local.database.entity.UpcomingMovieEntity
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.utilities.Constants
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