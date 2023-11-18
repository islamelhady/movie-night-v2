package com.elhady.movies.domain.usecases.favList

import com.elhady.movies.data.repository.AccountRepository
import com.elhady.movies.data.repository.MovieRepository
import javax.inject.Inject

class CreateListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(listName: String) {
        val sessionId = accountRepository.getSessionId()
        sessionId?.let {
            movieRepository.createList(sessionId = it, name = listName)
        }
    }
}