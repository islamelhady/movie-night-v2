package com.elhady.movies.data.local.mappers

import com.elhady.movies.data.local.database.entity.MysteryMovieEntity
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.utilities.Constant
import javax.inject.Inject

class MysteryMoviesMapper @Inject constructor() : Mapper<MovieDto, MysteryMovieEntity> {
    override fun map(input: MovieDto): MysteryMovieEntity {
        return MysteryMovieEntity(
            id = input.id ?: 0,
            name = input.title ?: "",
            imageUrl = (Constant.IMAGE_PATH + input.posterPath)
        )
    }
}