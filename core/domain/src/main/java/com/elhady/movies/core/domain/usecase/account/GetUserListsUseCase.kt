package com.elhady.movies.core.domain.usecase.account

import com.elhady.movies.core.common.MediaType
import com.elhady.movies.core.domain.model.account.UserList
import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class GetUserListsUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(mediaId: Int? = null, mediaType: MediaType = MediaType.MOVIE): List<UserList> {
        return accountRepository.getUserLists(mediaId, mediaType)
    }
}
