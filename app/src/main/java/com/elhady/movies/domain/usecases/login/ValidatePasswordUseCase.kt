package com.elhady.movies.domain.usecases.login

import com.elhady.movies.utilities.FormFieldState
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): FormFieldState {
        return if (password.length < 4) {
            FormFieldState.InValid("Minimum 4 character password")
        } else {
            FormFieldState.Valid
        }
    }
}