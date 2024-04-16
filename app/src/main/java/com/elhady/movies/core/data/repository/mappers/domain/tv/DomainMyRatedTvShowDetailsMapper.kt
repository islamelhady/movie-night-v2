package com.elhady.movies.core.data.repository.mappers.domain.tv

import com.elhady.movies.core.data.remote.response.dto.myrated.MyRatedTvShowDto
import com.elhady.movies.core.data.repository.Constants.IMAGE_BASE_PATH
import com.elhady.movies.core.domain.entities.myrated.MyRatedTvShowEntity
import com.elhady.movies.core.mapper.Mapper
import javax.inject.Inject

class DomainMyRatedTvShowDetailsMapper @Inject constructor() : Mapper<MyRatedTvShowDto, MyRatedTvShowEntity>{
    override fun map(input: MyRatedTvShowDto): MyRatedTvShowEntity {
        return MyRatedTvShowEntity(
            id = input.id ?: 0,
            title = input.name ?: "",
            imageUrl = IMAGE_BASE_PATH + input.posterPath,
            genreEntities = emptyList(),
            rate = input.rating ?: 0.0,
        )
    }
}