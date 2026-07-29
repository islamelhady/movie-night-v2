package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.tvshow.TvDetailsDto
import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.model.tvshow.TvDetailsInfo
import javax.inject.Inject

class TvDetailsDtoMapper @Inject constructor() :
    Mapper<TvDetailsDto, TvDetailsInfo> {
    override fun map(input: TvDetailsDto): TvDetailsInfo {
        return TvDetailsInfo(
            backdropImageUrl = BuildConfig.IMAGE_BASE_PATH + input.backdropPath,
            name = input.name ?: "",
            rating = input.voteAverage?.toFloat()?.times(0.5f) ?: 0.0f,
            description = input.overview ?: "",
            genres = mapGenreToEntity(input.genres)
        )
    }

    private fun mapGenreToEntity(genreDto: List<TvDetailsDto.Genre?>?): List<Genre> {
        return genreDto?.map {
            Genre(
                genreID = it?.id ?: 0,
                genreName = it?.name ?: ""
            )
        } ?: emptyList()
    }
}
