package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.network.dto.account.MyRatedTvShowDto
import com.elhady.movies.core.domain.model.account.MyRatedTvShow
import com.elhady.movies.core.common.Mapper
import javax.inject.Inject

class MyRatedTvShowDtoMapper @Inject constructor() : Mapper<MyRatedTvShowDto, MyRatedTvShow>{
    override fun map(input: MyRatedTvShowDto): MyRatedTvShow {
        return MyRatedTvShow(
            id = input.id ?: 0,
            title = input.name ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            genreEntities = emptyList(),
            rate = input.rating ?: 0.0,
        )
    }
}
