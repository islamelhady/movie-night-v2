package com.elhady.movies.data.local.mappers

import com.elhady.movies.data.local.database.entity.TopRatedMovieEntity
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.utilities.Constants
import javax.inject.Inject


class TopRatedMovieMapper @Inject constructor() : Mapper<MovieDto, TopRatedMovieEntity> {
    override fun map(input: MovieDto): TopRatedMovieEntity {
        return TopRatedMovieEntity(
            id = input.id ?: 0,
            name = input.title ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }

}