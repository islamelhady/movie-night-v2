package com.elhady.viewmodel.category


sealed interface CategoryUiEvent{
    data class ClickCategoryEvent(val categoryId: Int = FIRST_CATEGORY_ID): CategoryUiEvent
    object ClickRetry: CategoryUiEvent
    data class ClickMediaEvent(val mediaId: Int): CategoryUiEvent
}