package com.elhady.movies.core.domain.usecase.details.moviedetails

import com.elhady.movies.core.domain.model.StatusEntity
import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class CreateUserListUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(listName: String): StatusEntity {
        return accountRepository.createUserList(listName)
    }
}
