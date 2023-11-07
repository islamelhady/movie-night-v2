package com.elhady.movies.ui.category

import com.elhady.movies.utilities.Constants.FIRST_CATEGORY_ID

sealed interface CategoryUiEvent{
    data class ClickCategoryEvent(val categoryId: Int = FIRST_CATEGORY_ID): CategoryUiEvent
    object ClickRetry: CategoryUiEvent
}