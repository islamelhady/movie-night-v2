package com.elhady.movies.core.data.mapper.search

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.network.dto.tvshow.TvDto
import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.model.tvshow.Tv
import javax.inject.Inject

class DomainTvShowSearchMapper @Inject constructor() {
    fun map(input: TvDto, genres: List<Genre>): Tv {
        return Tv(
            id = input.id ?: 0,
            title = input.name ?: "",
            rate = input.voteAverage ?: 0.0,
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            year = input.firstAirDate ?: "",
            genreEntities = filterGenres(genresIds = input.genreIds?.filterNotNull() ?: emptyList(), genresEntities = genres)
        )
    }

    fun map(input: List<TvDto>, genres: List<Genre>): List<Tv> {
        return input.map {
            map(it, genres)
        }
    }

    private fun filterGenres(
        genresIds: List<Int>,
        genresEntities: List<Genre>
    ): List<Genre> {
        return genresEntities.filter { it.genreID in genresIds }
    }
}
