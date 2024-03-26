package com.elhady.movies.core.data.repository.mappers.cash.tv

import com.elhady.movies.BuildConfig
import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.local.database.dto.tvshow.TvShowsLocalDto
import com.elhady.movies.core.data.remote.response.dto.TVShowsRemoteDto
import javax.inject.Inject

class LocalTvShowMapper @Inject constructor():
    Mapper<TVShowsRemoteDto, TvShowsLocalDto> {
    override fun map(input: TVShowsRemoteDto): TvShowsLocalDto {
        return TvShowsLocalDto(
            id = input.id ?: 0,
            title = input.name ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            rate = input.voteAverage ?: 0.0
        )
    }
}