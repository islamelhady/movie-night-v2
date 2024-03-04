package com.elhady.repository.mappers.cash.movies

import com.elhady.local.Constants
import com.elhady.local.database.dto.movies.MysteryMovieLocalDto
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.dto.MovieRemoteDto
import javax.inject.Inject

class LocalMysteryMoviesMapper @Inject constructor() : Mapper<MovieRemoteDto, MysteryMovieLocalDto> {
    override fun map(input: MovieRemoteDto): MysteryMovieLocalDto {
        return MysteryMovieLocalDto(
            id = input.id ?: 0,
            name = input.title ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}