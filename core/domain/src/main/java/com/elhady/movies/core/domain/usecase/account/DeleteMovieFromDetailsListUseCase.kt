package com.elhady.movies.core.domain.usecase.account

import com.elhady.movies.core.domain.model.common.Status
import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class DeleteMovieFromDetailsListUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(listId: Int,mediaId: Int ): Status {
        return accountRepository.deleteMovieDetailsList(listId =listId , mediaId = mediaId )
    }
}
