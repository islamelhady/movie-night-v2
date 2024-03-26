package com.elhady.movies.core.data.repository.mappers.cash.tv

import com.elhady.movies.BuildConfig
import com.elhady.movies.core.data.local.database.dto.tvshow.AiringTodayTvShowsLocalDto
import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.remote.response.dto.TVShowsRemoteDto
import javax.inject.Inject

class LocalAiringTodayTvShowMapper @Inject constructor():
    Mapper<TVShowsRemoteDto, AiringTodayTvShowsLocalDto> {
    override fun map(input: TVShowsRemoteDto): AiringTodayTvShowsLocalDto {
        return AiringTodayTvShowsLocalDto(
            id = input.id ?: 0,
            title = input.name ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            rate = input.voteAverage ?: 0.0
        )
    }
}