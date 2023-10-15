package com.elhady.movies.data.local.mappers.movies

import com.elhady.movies.data.local.database.entity.movies.NowPlayingMovieEntity
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.utilities.Constants
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