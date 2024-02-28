package com.elhady.usecase.util

sealed interface FormFieldState{
    object Valid: FormFieldState
    data class InValid(val message: String): FormFieldState
    fun isValid(): Boolean{
        return this is Valid
    }

    fun errorMessage() = if (this is InValid) message else null

}