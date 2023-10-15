package com.elhady.movies.data.local.mappers

import com.elhady.movies.data.local.database.entity.TrendingMovieEntity
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.utilities.Constant
import javax.inject.Inject

class TrendingMovieMapper @Inject constructor(): Mapper<MovieDto, TrendingMovieEntity> {
    override fun map(input: MovieDto): TrendingMovieEntity {
        return TrendingMovieEntity(
            id = input.id ?: 0,
            name = input.title ?: "",
            imageUrl = (Constant.IMAGE_PATH + input.posterPath)
        )
    }
}