package com.elhady.movies.feature.player.presentation.player

sealed interface PlayerUiEffect {
    object NavigateBack : PlayerUiEffect
}
