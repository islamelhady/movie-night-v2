package com.elhady.movies.core.data.mapper.account

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.network.dto.account.MyRatedTvShowDto
import com.elhady.movies.core.domain.model.common.GenreEntity
import com.elhady.movies.core.domain.model.account.MyRatedTvShowEntity
import javax.inject.Inject

class DomainMyRatedTvShowMapper @Inject constructor() {

    fun map(input: MyRatedTvShowDto, genreEntities: List<GenreEntity>): MyRatedTvShowEntity {
        return MyRatedTvShowEntity(
            id = input.id ?: 0,
            title = input.name ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            genreEntities = genreEntities.filter {
                it.genreID in (input.genreIds?.filterNotNull() ?: emptyList())
            },
            rate = input.rating ?: 0.0,
            year = input.firstAirDate ?: ""
        )
    }
}
