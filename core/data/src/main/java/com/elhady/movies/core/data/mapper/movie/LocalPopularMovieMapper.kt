package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.entity.movie.PopularMovieEntity
import com.elhady.movies.core.network.dto.movie.MovieDto
import javax.inject.Inject

class LocalPopularMovieMapper @Inject constructor() :
    Mapper<MovieDto, PopularMovieEntity> {
    override fun map(input: MovieDto): PopularMovieEntity {
        return PopularMovieEntity(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            rate = input.voteAverage ?: 0.0
        )
    }
}
