package com.elhady.local.mappers.movies

import com.elhady.local.Constants
import com.elhady.local.database.entity.movies.TrendingMovieEntity
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.dto.MovieDto
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