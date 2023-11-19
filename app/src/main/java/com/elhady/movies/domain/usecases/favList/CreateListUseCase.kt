package com.elhady.movies.domain.usecases.favList

import com.elhady.movies.data.repository.AccountRepository
import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.models.CreatedList
import javax.inject.Inject

class CreateListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val accountRepository: AccountRepository,
    private val getCreatedListUseCase: GetCreatedListUseCase
) {
    suspend operator fun invoke(listName: String): List<CreatedList> {
        val sessionId = accountRepository.getSessionId()
        return sessionId?.let {
            val item = movieRepository.createList(sessionId = it, name = listName)
            if (item?.success == true) {
                getCreatedListUseCase()
            } else {
                throw Throwable("failed")
            }
        } ?: throw Throwable("not success")
    }
}