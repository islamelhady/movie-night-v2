package com.elhady.movies.core.common.domain.usecase

import com.elhady.movies.core.common.domain.entities.GenreEntity
import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class GetAllGenresTvsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): List<GenreEntity>{
        repository.refreshGenresTv()
        return repository.getGenresTvs().sortedBy { it.genreName }
    }
}
