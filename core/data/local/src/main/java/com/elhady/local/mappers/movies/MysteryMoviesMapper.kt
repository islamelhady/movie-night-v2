package com.elhady.local.mappers.movies

import com.elhady.local.Constants
import com.elhady.local.database.entity.movies.MysteryMovieEntity
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.dto.MovieDto
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