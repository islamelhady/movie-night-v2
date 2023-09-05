package com.elhady.movies.utilities

sealed interface FormFieldState{
    object Valid: FormFieldState
    data class InValid(val message: String): FormFieldState
}