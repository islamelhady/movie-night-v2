package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.people.People
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class GetTvDetailsCastUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository,
) {
    suspend operator fun invoke(tvShowId:Int): List<People> {
        return tvShowRepository.getTvDetailsCast(tvShowId)
    }
}
