package com.elhady.movies.data.local.mappers

import com.elhady.movies.data.local.database.entity.AdventureMovieEntity
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.utilities.Constant
import javax.inject.Inject

class AdventureMoviesMapper @Inject constructor() : Mapper<MovieDto, AdventureMovieEntity> {
    override fun map(input: MovieDto): AdventureMovieEntity {
        return AdventureMovieEntity(
            id = input.id ?: 0,
            name = input.title ?: "",
            imageUrl = (Constant.IMAGE_PATH + input.posterPath)
        )
    }
}