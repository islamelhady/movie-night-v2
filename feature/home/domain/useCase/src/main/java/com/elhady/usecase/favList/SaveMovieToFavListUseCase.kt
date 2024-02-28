package com.elhady.usecase.favList

import com.elhady.usecase.repository.AccountRepository
import com.elhady.usecase.repository.MovieRepository
import javax.inject.Inject

class SaveMovieToFavListUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(listId: Int, mediaId: Int): String {
        val result = movieRepository.getListDetails(listId)

        return if (result?.checkIfExist(mediaId) == true) {
            "Fail: this movie is already on the list"
        } else {
            addMovieToList(listId, mediaId)
        }
    }

    private suspend fun addMovieToList(listId: Int, mediaId: Int): String {
        val sessionId = accountRepository.getSessionId()
        return sessionId?.let {
            movieRepository.addMovieToList(it, listId, mediaId)
            "Success: The movie has been added"
        } ?: throw Throwable("no login")
    }

}