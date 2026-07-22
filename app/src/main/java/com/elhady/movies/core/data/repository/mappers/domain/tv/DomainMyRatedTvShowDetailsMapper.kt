package com.elhady.movies.core.data.repository.mappers.domain.tv

import com.elhady.movies.BuildConfig
import com.elhady.movies.core.network.model.response.dto.myrated.MyRatedTvShowDto
import com.elhady.movies.core.common.domain.entities.myrated.MyRatedTvShowEntity
import com.elhady.movies.core.common.mapper.Mapper
import javax.inject.Inject

class DomainMyRatedTvShowDetailsMapper @Inject constructor() : Mapper<MyRatedTvShowDto, MyRatedTvShowEntity>{
    override fun map(input: MyRatedTvShowDto): MyRatedTvShowEntity {
        return MyRatedTvShowEntity(
            id = input.id ?: 0,
            title = input.name ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            genreEntities = emptyList(),
            rate = input.rating ?: 0.0,
        )
    }
}
