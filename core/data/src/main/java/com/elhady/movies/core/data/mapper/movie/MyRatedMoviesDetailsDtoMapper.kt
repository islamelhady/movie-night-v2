package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.network.dto.account.MyRatedMovieDto
import com.elhady.movies.core.domain.model.account.MyRatedMovie
import com.elhady.movies.core.common.Mapper
import javax.inject.Inject

class MyRatedMoviesDetailsDtoMapper @Inject constructor() : Mapper<MyRatedMovieDto, MyRatedMovie>{
    override fun map(input: MyRatedMovieDto): MyRatedMovie {
        return MyRatedMovie(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            genreEntities = emptyList(),
            myRate = input.rating ?: 0.0,
            year = input.releaseDate ?: ""
        )
    }
}
