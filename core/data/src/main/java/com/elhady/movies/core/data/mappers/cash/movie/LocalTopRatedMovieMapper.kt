package com.elhady.movies.core.data.mappers.cash.movie

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.dto.movie.TopRatedMovieLocalDto
import com.elhady.movies.core.network.model.response.dto.MovieRemoteDto
import javax.inject.Inject

class LocalTopRatedMovieMapper @Inject constructor():
    Mapper<MovieRemoteDto, TopRatedMovieLocalDto> {
    override fun map(input: MovieRemoteDto): TopRatedMovieLocalDto {
        return TopRatedMovieLocalDto(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            rate = input.voteAverage ?: 0.0
        )
    }
}
