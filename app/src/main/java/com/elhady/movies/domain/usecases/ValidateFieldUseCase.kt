package com.elhady.movies.domain.usecases

import javax.inject.Inject

class ValidateFieldUseCase @Inject constructor(
    private val validateLoginFieldUseCase: ValidateLoginFieldUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) {

    operator fun invoke(userName: String, password: String): Boolean {
        return validateLoginFieldUseCase(userName).isValid() && validatePasswordUseCase(password).isValid()
    }
}