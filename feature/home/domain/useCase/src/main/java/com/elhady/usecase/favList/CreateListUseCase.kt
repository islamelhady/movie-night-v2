package com.elhady.usecase.favList

import com.elhady.entities.CreatedListEntity
import com.elhady.usecase.repository.AuthRepository
import com.elhady.usecase.repository.MovieRepository
import javax.inject.Inject

class CreateListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val accountRepository: AuthRepository,
    private val getCreatedListUseCase: GetCreatedListUseCase
) {
    suspend operator fun invoke(listName: String): List<CreatedListEntity> {
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