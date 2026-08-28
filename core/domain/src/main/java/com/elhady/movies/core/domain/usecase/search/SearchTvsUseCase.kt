package com.elhady.movies.core.domain.usecase.search

import com.elhady.movies.core.domain.model.tvshow.Tv
import com.elhady.movies.core.domain.repository.SearchRepository
import javax.inject.Inject

class SearchTvsUseCase @Inject constructor(
    private val searchRepository: SearchRepository,

    ) {
    suspend operator fun invoke(
        keyword: String,
        genreId: Int? = null
    ): List<Tv> {
        return searchRepository.searchForTv(keyword)
            .filter { tv ->
                ((genreId == null) || tv.genreEntities.any { it.genreID == genreId }) && tv.rate != 0.0
            }
            .sortedByDescending { it.rate }
    }
}
