package com.elhady.repository.mappers.cash.movies

import com.elhady.local.Constants
import com.elhady.local.database.dto.movies.TrendingMovieLocalDto
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.dto.MovieRemoteDto
import javax.inject.Inject

class LocalTrendingMovieMapper @Inject constructor(): Mapper<MovieRemoteDto, TrendingMovieLocalDto> {
    override fun map(input: MovieRemoteDto): TrendingMovieLocalDto {
        return TrendingMovieLocalDto(
            id = input.id ?: 0,
            name = input.title ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}