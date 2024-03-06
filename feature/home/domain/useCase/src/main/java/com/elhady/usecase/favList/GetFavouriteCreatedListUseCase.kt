package com.elhady.usecase.favList

import com.elhady.entities.CreatedListEntity
import com.elhady.usecase.repository.AuthRepository
import com.elhady.usecase.repository.MovieRepository
import javax.inject.Inject
class GetFavouriteCreatedListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val accountRepository: AuthRepository,
    private val getFavListDetailsUseCase: GetFavListDetailsUseCase
) {
    suspend operator fun invoke(): List<CreatedListEntity> {
        val sessionId = accountRepository.getSessionId()
        return if (!sessionId.isNullOrEmpty()) {
            val response = movieRepository.getCreatedList(sessionId)
            response.let{ items ->
                items!!.map {
                    CreatedListEntity(
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