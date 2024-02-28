package com.elhady.usecase.favList

import com.elhady.entities.CreatedListEntity
import com.elhady.usecase.repository.AccountRepository
import com.elhady.usecase.repository.MovieRepository
import javax.inject.Inject

class GetCreatedListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val accountRepository: AccountRepository,
//    private val createdListMapper: CreatedListMapper
) {
    suspend operator fun invoke(): List<CreatedListEntity> {
        val sessionId = accountRepository.getSessionId()
        return if (!sessionId.isNullOrEmpty()) {
            val response = movieRepository.getCreatedList(sessionId)
            response?.let (createdListMapper::map) ?: throw Throwable("not found")
        } else {
            throw Throwable("no login")
        }
    }
}