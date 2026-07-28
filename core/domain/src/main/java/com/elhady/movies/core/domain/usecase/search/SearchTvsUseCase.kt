package com.elhady.movies.core.domain.usecase.search

import com.elhady.movies.core.domain.model.TvEntity
import com.elhady.movies.core.domain.repository.SearchRepository
import javax.inject.Inject

class SearchTvsUseCase @Inject constructor(
    private val searchRepository: SearchRepository,

    ) {
    suspend operator fun invoke(
        keyword: String,
        genreId: Int? = null
    ): List<TvEntity> {
        return searchRepository.searchForTv(keyword).filter { tv ->
            tv.genreEntities.takeIf { genreId != null }
                ?.map { it.genreID }
                ?.contains(genreId) ?: true && tv.rate != 0.0
        }.sortedByDescending { it.rate }
    }
}
