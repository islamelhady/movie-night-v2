package com.elhady.repository.mappers.cash.movies

import com.elhady.local.Constants
import com.elhady.local.database.dto.movies.AdventureMovieLocalDto
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.dto.MovieRemoteDto
import javax.inject.Inject

class LocalAdventureMoviesMapper @Inject constructor() : Mapper<MovieRemoteDto, AdventureMovieLocalDto> {
    override fun map(input: MovieRemoteDto): AdventureMovieLocalDto {
        return AdventureMovieLocalDto(
            id = input.id ?: 0,
            name = input.title ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}