package com.elhady.movies.data.local.mappers.movies

import com.elhady.movies.data.local.database.entity.movies.TrendingMovieEntity
import com.elhady.movies.data.remote.response.movie.MovieDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class TrendingMovieMapper @Inject constructor(): Mapper<MovieDto, TrendingMovieEntity> {
    override fun map(input: MovieDto): TrendingMovieEntity {
        return TrendingMovieEntity(
            id = input.id ?: 0,
            name = input.title ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}