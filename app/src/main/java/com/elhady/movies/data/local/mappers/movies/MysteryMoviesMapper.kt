package com.elhady.movies.data.local.mappers.movies

import com.elhady.movies.data.local.database.entity.movies.MysteryMovieEntity
import com.elhady.movies.data.remote.response.movie.MovieDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class MysteryMoviesMapper @Inject constructor() : Mapper<MovieDto, MysteryMovieEntity> {
    override fun map(input: MovieDto): MysteryMovieEntity {
        return MysteryMovieEntity(
            id = input.id ?: 0,
            name = input.title ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}