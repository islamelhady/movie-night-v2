package com.elhady.movies.ui.category

import com.elhady.movies.utilities.Constants.ADVENTURE_ID

sealed interface CategoryUiEvent{
    data class ClickCategoryEvent(val categoryId: Int = ADVENTURE_ID): CategoryUiEvent
    object ClickRetry: CategoryUiEvent
}