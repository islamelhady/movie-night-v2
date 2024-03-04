package com.elhady.repository.mappers.cash.movies

import com.elhady.local.Constants
import com.elhady.local.database.dto.movies.NowPlayingMovieLocalDto
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.dto.MovieRemoteDto
import javax.inject.Inject

class LocalNowPlayingMovieMapper @Inject constructor() : Mapper<MovieRemoteDto, NowPlayingMovieLocalDto> {
    override fun map(input: MovieRemoteDto): NowPlayingMovieLocalDto {
        return NowPlayingMovieLocalDto(
            id = input.id ?: 0,
            name = input.title ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}