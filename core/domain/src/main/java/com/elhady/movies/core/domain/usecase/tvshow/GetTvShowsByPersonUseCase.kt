package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.tvshow.TvShowEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class GetTvShowsByPersonUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository,
) {
    suspend operator fun invoke(personId:Int): List<TvShowEntity> {
        return tvShowRepository.getTvShowsByPerson(personId)
    }
}
