package com.elhady.movies.domain.usecases

import com.elhady.movies.utilities.FormFieldState
import javax.inject.Inject

class ValidateFieldUseCase @Inject constructor(){
    operator fun invoke(text: String): FormFieldState {
        return if (text.isEmpty() || text.isBlank()) {
            FormFieldState.InValid("Required")
        } else {
            FormFieldState.Valid
        }
    }
}