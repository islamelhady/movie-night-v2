package com.elhady.movies.domain.usecases.favList

import com.elhady.movies.data.repository.AccountRepository
import com.elhady.movies.data.repository.MovieRepository
import javax.inject.Inject

class CreateListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(listName: String): Boolean {
        val sessionId = accountRepository.getSessionId()
        return sessionId?.let {
            val item = movieRepository.createList(sessionId = it, name = listName)
            if (item?.success == true){
                throw Throwable("Success")
            }else{
                throw Throwable("failed")
            }
        }?: throw Throwable("not success")
    }
}