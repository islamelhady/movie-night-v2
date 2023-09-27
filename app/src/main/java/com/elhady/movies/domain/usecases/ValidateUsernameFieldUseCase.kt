package com.elhady.movies.domain.usecases

import com.elhady.movies.utilities.FormFieldState
import javax.inject.Inject

class ValidateUsernameFieldUseCase @Inject constructor(){
    operator fun invoke(username: String): FormFieldState {
        return if (username.isEmpty() || username.isBlank()) {
            FormFieldState.InValid("Required")
        } else {
            FormFieldState.Valid
        }
    }
}