package com.elhady.movies.domain.usecases.favList

import com.elhady.movies.data.repository.AccountRepository
import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.models.CreatedList
import javax.inject.Inject
class GetFavouriteCreatedListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val accountRepository: AccountRepository,
    private val getFavListDetailsUseCase: GetFavListDetailsUseCase
) {
    suspend operator fun invoke(): List<CreatedList> {
        val sessionId = accountRepository.getSessionId()
        return if (!sessionId.isNullOrEmpty()) {
            val response = movieRepository.getCreatedList(sessionId)
            response.let{ items ->
                items!!.map {
                    CreatedList(
                        id = it.id ?: 0,
                        itemCount = it.itemCount ?: 0,
                        name = it.name ?: "",
                        listType = it.listType ?: "",
                        posterPath = getFavListDetailsUseCase(listID = it.id ?: 0).map {
                            it.posterPath
                        }
                    )
                }
            }
        } else {
            throw Throwable("no login")
        }
    }
}