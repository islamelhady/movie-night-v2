package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.AccountRepository
import javax.inject.Inject

class LoginWithUsernameAndPasswordUseCase @Inject constructor(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(userName: String, password: String): Boolean {
        return accountRepository.loginWithUsernameAndPassword(userName, password)
    }
}