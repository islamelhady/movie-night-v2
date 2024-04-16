package com.elhady.movies.core.data.repository.mappers.domain.myrated

import com.elhady.movies.core.data.remote.response.dto.myrated.MyRatedTvShowDto
import com.elhady.movies.core.data.repository.Constants.IMAGE_BASE_PATH
import com.elhady.movies.core.domain.entities.GenreEntity
import com.elhady.movies.core.domain.entities.myrated.MyRatedTvShowEntity
import javax.inject.Inject

class DomainMyRatedTvShowMapper @Inject constructor() {

    fun map(input: MyRatedTvShowDto, genreEntities: List<GenreEntity>): MyRatedTvShowEntity {
        return MyRatedTvShowEntity(
            id = input.id ?: 0,
            title = input.name ?: "",
            imageUrl = IMAGE_BASE_PATH + input.posterPath,
            genreEntities = genreEntities.filter {
                it.genreID in (input.genreIds?.filterNotNull() ?: emptyList())
            },
            rate = input.rating ?: 0.0,
            year = input.firstAirDate ?: ""
        )
    }
}