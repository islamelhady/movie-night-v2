package com.elhady.local.mappers.movies

import com.elhady.local.Constants
import com.elhady.local.database.entity.movies.TopRatedMovieEntity
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.dto.MovieDto
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