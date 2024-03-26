package com.elhady.movies.core.data.repository.mappers.domain.movie

import com.elhady.movies.BuildConfig
import com.elhady.movies.core.data.remote.response.dto.myrated.MyRatedMovieDto
import com.elhady.movies.core.domain.entities.myrated.MyRatedMovieEntity
import com.elhady.movies.core.mapper.Mapper
import javax.inject.Inject

class DomainMyRatedMoviesDetailsMapper @Inject constructor() : Mapper<MyRatedMovieDto, MyRatedMovieEntity>{
    override fun map(input: MyRatedMovieDto): MyRatedMovieEntity {
        return MyRatedMovieEntity(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            genreEntities = emptyList(),
            myRate = input.rating ?: 0.0,
            year = input.releaseDate ?: ""
        )
    }
}