package com.elhady.repository.mappers.domain

import com.elhady.entities.PopularMovieEntity
import com.elhady.local.Constants
import com.elhady.local.database.entity.movies.PopularMovieLocalDto
import com.elhady.repository.mappers.Mapper
import javax.inject.Inject

class DomainPopularMovieMapper @Inject constructor()
    : Mapper<PopularMovieLocalDto, PopularMovieEntity> {
    override fun map(input: PopularMovieLocalDto): PopularMovieEntity {
        return PopularMovieEntity(
            movieId = input.id,
            imageUrl = (Constants.IMAGE_PATH + input.imageUrl),
            title = input.title,
            genre = emptyList(),
            movieRate = input.movieRate
        )
    }
}