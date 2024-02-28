package com.elhady.usecase

import com.elhady.usecase.util.FormFieldState
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