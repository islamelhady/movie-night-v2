package com.elhady.movies.core.domain.usecase.common

import com.elhady.movies.core.domain.repository.MovieRepository
import com.elhady.movies.core.domain.utils.Clock
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RefreshIfNeededUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val clock: Clock
) {
    private val mutex = Mutex()

    suspend operator fun invoke(): Boolean {
        mutex.withLock {
            val lastRefreshTime = movieRepository.getLastRefreshTime()
            val currentTime = clock.now()

            if (lastRefreshTime == null ||
                currentTime - lastRefreshTime >= TimeUnit.DAYS.toMillis(1)
            ) {
                movieRepository.refreshAll()
                movieRepository.setLastRefreshTime(currentTime)
                return true
            }
        }
        return false
    }
}
