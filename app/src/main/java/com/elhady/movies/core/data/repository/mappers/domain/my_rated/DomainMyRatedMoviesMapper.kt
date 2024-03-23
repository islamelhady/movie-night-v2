package com.elhady.movies.core.data.repository.mappers.domain.my_rated

import com.elhady.movies.core.data.remote.response.dto.myrated.MyRatedMovieDto
import com.elhady.movies.core.data.repository.Constants.IMAGE_BASE_PATH
import com.elhady.movies.core.domain.entities.GenreEntity
import com.elhady.movies.core.domain.entities.myrated.MyRatedMovieEntity
import javax.inject.Inject

class DomainMyRatedMoviesMapper @Inject constructor() {
    fun map(input: MyRatedMovieDto, genreEntities: List<GenreEntity>): MyRatedMovieEntity {
        return MyRatedMovieEntity(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = IMAGE_BASE_PATH + input.posterPath,
            genreEntities = genreEntities.filter {
                it.genreID in (input.genreIds?.filterNotNull() ?: emptyList())
            },
            myRate = input.rating ?: 0.0,
            year = input.releaseDate ?: ""
        )
    }
}