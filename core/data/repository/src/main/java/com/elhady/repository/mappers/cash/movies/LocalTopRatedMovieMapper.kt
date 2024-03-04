package com.elhady.repository.mappers.cash.movies

import com.elhady.local.Constants
import com.elhady.local.database.dto.movies.TopRatedMovieLocalDto
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.dto.MovieRemoteDto
import javax.inject.Inject


class LocalTopRatedMovieMapper @Inject constructor() : Mapper<MovieRemoteDto, TopRatedMovieLocalDto> {
    override fun map(input: MovieRemoteDto): TopRatedMovieLocalDto {
        return TopRatedMovieLocalDto(
            id = input.id ?: 0,
            name = input.title ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }

}