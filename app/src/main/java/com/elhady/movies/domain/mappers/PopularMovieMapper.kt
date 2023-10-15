package com.elhady.movies.domain.mappers

import com.elhady.movies.data.local.database.entity.movies.PopularMovieEntity
import com.elhady.movies.domain.models.PopularMovie
import javax.inject.Inject

class PopularMovieMapper @Inject constructor(): Mapper<PopularMovieEntity, PopularMovie> {
    override fun map(input: PopularMovieEntity): PopularMovie {
        return PopularMovie(
            movieId = input.id,
            imageUrl = input.imageUrl,
            title = input.title,
            movieRate = input.movieRate,
            genre = input.genres
        )
    }
}