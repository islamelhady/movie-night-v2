package com.elhady.movies.domain.usecases

import javax.inject.Inject

class ValidateFieldUseCase @Inject constructor(
    private val validateUsernameFieldUseCase: ValidateUsernameFieldUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) {

    operator fun invoke(username: String, password: String): Boolean {
        return validateUsernameFieldUseCase(username).isValid() && validatePasswordUseCase(password).isValid()
    }
}