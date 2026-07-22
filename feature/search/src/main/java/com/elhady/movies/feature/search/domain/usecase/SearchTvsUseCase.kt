package com.elhady.movies.feature.search.domain.usecase

import com.elhady.movies.core.common.domain.entities.TvEntity
import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class SearchTvsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,

    ) {
    suspend operator fun invoke(
        keyword: String,
        genreId: Int? = null
    ): List<TvEntity> {
        return movieRepository.searchForTv(keyword).filter { tv ->
            tv.genreEntities.takeIf { genreId != null }
                ?.map { it.genreID }
                ?.contains(genreId) ?: true && tv.rate != 0.0
        }.sortedByDescending { it.rate }
    }
}
