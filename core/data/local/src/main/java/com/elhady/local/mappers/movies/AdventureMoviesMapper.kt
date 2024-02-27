package com.elhady.local.mappers.movies

import com.elhady.local.Constants
import com.elhady.local.database.entity.movies.AdventureMovieEntity
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.dto.MovieDto
import javax.inject.Inject

class AdventureMoviesMapper @Inject constructor() : Mapper<MovieDto, AdventureMovieEntity> {
    override fun map(input: MovieDto): AdventureMovieEntity {
        return AdventureMovieEntity(
            id = input.id ?: 0,
            name = input.title ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}