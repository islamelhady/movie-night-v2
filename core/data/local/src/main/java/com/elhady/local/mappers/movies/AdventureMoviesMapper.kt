package com.elhady.local.mappers.movies

import com.elhady.local.Constants
import com.elhady.local.database.entity.movies.AdventureMovieLocalDto
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.dto.MovieDto
import javax.inject.Inject

class AdventureMoviesMapper @Inject constructor() : Mapper<MovieDto, AdventureMovieLocalDto> {
    override fun map(input: MovieDto): AdventureMovieLocalDto {
        return AdventureMovieLocalDto(
            id = input.id ?: 0,
            name = input.title ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}