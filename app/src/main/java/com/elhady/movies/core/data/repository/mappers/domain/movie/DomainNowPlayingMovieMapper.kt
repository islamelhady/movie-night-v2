package com.elhady.movies.core.data.repository.mappers.domain.movie

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.local.database.dto.movie.NowPlayingMovieLocalDto
import com.elhady.movies.core.domain.entities.MovieEntity
import javax.inject.Inject

class DomainNowPlayingMovieMapper @Inject constructor():
    Mapper<NowPlayingMovieLocalDto, MovieEntity> {

    override fun map(input: NowPlayingMovieLocalDto): MovieEntity {
        return MovieEntity(
            id = input.id,
            title = input.title,
            imageUrl = input.imageUrl,
            genreEntities = emptyList(),
            rate = input.rate
        )
    }
}