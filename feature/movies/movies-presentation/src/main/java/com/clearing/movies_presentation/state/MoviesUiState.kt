package com.clearing.movies_presentation.state

import androidx.compose.runtime.Immutable
import com.clearing.core_ui.state.UiState

@Immutable
data class MoviesUiState(
    val favoritesIds: List<Int> = emptyList(),
    val uiState: UiState<Unit> = UiState.Loading,
    val showOnlyFavorites: Boolean = false,
)
