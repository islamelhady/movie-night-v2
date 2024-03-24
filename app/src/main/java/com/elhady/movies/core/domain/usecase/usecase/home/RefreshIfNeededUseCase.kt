package com.elhady.movies.core.domain.usecase.usecase.home

import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import com.elhady.movies.core.domain.usecase.repository.TvShowRepository
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RefreshIfNeededUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val currentDate: Date
) {
    suspend operator fun invoke(): Boolean {
        val lastRefreshTime = movieRepository.getLastRefreshTime()

        if (lastRefreshTime == null ||
            currentDate.time - lastRefreshTime >= TimeUnit.DAYS.toMillis(1)
        ) {
            movieRepository.setLastRefreshTime(currentDate.time)
            movieRepository.refreshAll()
            return true
        }

        return false
    }
}