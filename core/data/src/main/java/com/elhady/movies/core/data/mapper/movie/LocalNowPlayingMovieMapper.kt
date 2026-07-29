package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.dto.movie.NowPlayingMovieLocalDto
import com.elhady.movies.core.network.dto.movie.MovieDto
import javax.inject.Inject

class LocalNowPlayingMovieMapper @Inject constructor() :
    Mapper<MovieDto, NowPlayingMovieLocalDto> {
    override fun map(input: MovieDto): NowPlayingMovieLocalDto {
        return NowPlayingMovieLocalDto(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            rate = input.voteAverage ?: 0.0
        )
    }
}
