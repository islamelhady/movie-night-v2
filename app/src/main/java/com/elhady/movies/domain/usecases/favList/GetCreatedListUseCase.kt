package com.elhady.movies.domain.usecases.favList

import com.elhady.movies.data.repository.AccountRepository
import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.favList.CreatedListMapper
import com.elhady.movies.domain.models.CreatedList
import javax.inject.Inject

class GetCreatedListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val accountRepository: AccountRepository,
    private val createdListMapper: CreatedListMapper
) {
    suspend operator fun invoke(): List<CreatedList> {
        val sessionId = accountRepository.getSessionId()
        return if (!sessionId.isNullOrEmpty()) {
            val response = movieRepository.getCreatedList(sessionId)
            response?.let { list ->
                list.map {
                    createdListMapper.map(it)
                }
            } ?: throw Throwable("not found")
        } else {
            throw Throwable("no login")
        }
    }
}