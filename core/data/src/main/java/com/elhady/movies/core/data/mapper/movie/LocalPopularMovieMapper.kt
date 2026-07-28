package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.dto.movie.PopularMovieLocalDto
import com.elhady.movies.core.network.model.response.dto.MovieRemoteDto
import javax.inject.Inject

class LocalPopularMovieMapper @Inject constructor() :
    Mapper<MovieRemoteDto, PopularMovieLocalDto> {
    override fun map(input: MovieRemoteDto): PopularMovieLocalDto {
        return PopularMovieLocalDto(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            rate = input.voteAverage ?: 0.0
        )
    }
}
