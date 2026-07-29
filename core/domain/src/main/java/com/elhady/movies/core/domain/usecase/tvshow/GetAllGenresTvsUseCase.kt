package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.repository.GenreRepository
import javax.inject.Inject

class GetAllGenresTvsUseCase @Inject constructor(
    private val repository: GenreRepository
) {
    suspend operator fun invoke(): List<Genre>{
        repository.refreshGenresTv()
        return repository.getGenresTvs().sortedBy { it.genreName }
    }
}
