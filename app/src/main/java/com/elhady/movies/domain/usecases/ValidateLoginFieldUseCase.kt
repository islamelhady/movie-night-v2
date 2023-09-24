package com.elhady.movies.domain.usecases

import com.elhady.movies.utilities.FormFieldState
import javax.inject.Inject

class ValidateLoginFieldUseCase @Inject constructor(){
    operator fun invoke(userName: String): FormFieldState {
        return if (userName.isEmpty() || userName.isBlank()) {
            FormFieldState.InValid("Required")
        } else {
            FormFieldState.Valid
        }
    }
}