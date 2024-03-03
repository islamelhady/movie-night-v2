package com.elhady.repository.mappers.domain

import com.elhady.entities.PopularMovieEntity
import com.elhady.local.Constants
import com.elhady.local.database.dto.movies.PopularMovieLocalDto
import com.elhady.repository.mappers.Mapper
import javax.inject.Inject

class DomainPopularMovieMapper @Inject constructor() :
    Mapper<PopularMovieLocalDto, PopularMovieEntity> {
    override fun map(movie: PopularMovieLocalDto): PopularMovieEntity {
        return PopularMovieEntity(
            movieId = movie.id ?: 0,
            movieName = movie.title ?: "",
            movieRate = movie.movieRate ?: 0.0,
            movieImage = (Constants.IMAGE_PATH + movie.imageUrl),
            movieGenre = movie.genres
        )
    }
}