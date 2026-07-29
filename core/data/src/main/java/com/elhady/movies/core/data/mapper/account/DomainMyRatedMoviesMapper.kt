package com.elhady.movies.core.data.mapper.account

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.network.dto.account.MyRatedMovieDto
import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.model.account.MyRatedMovie
import javax.inject.Inject

class DomainMyRatedMoviesMapper @Inject constructor() {
    fun map(input: MyRatedMovieDto, genreEntities: List<Genre>): MyRatedMovie {
        return MyRatedMovie(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            genreEntities = genreEntities.filter {
                it.genreID in (input.genreIds ?: emptyList())
            },
            myRate = input.rating ?: 0.0,
            year = input.releaseDate ?: ""
        )
    }
}
