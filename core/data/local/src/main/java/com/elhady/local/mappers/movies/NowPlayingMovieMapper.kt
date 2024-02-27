package com.elhady.local.mappers.movies

import com.elhady.local.Constants
import com.elhady.local.database.entity.movies.NowPlayingMovieEntity
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.dto.MovieDto
import javax.inject.Inject

class NowPlayingMovieMapper @Inject constructor() : Mapper<MovieDto, NowPlayingMovieEntity> {
    override fun map(input: MovieDto): NowPlayingMovieEntity {
        return NowPlayingMovieEntity(
            id = input.id ?: 0,
            name = input.title ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}