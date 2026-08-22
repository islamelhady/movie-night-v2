package com.elhady.movies.feature.showmore.presentation.showmore

import com.elhady.movies.core.domain.model.account.ListType

sealed interface ShowMoreUiEvent {
    object BackClicked : ShowMoreUiEvent
    data class ItemClicked(val id: Int, val type: ListType) : ShowMoreUiEvent
    object RetryClicked : ShowMoreUiEvent
}
