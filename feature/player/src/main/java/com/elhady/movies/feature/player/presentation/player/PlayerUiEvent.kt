package com.elhady.movies.feature.player.presentation.player

sealed interface PlayerUiEvent {
    object BackClicked : PlayerUiEvent
}
