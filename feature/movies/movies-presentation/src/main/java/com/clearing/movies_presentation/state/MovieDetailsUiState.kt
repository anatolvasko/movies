package com.clearing.movies_presentation.state

import androidx.compose.runtime.Immutable
import com.clearing.core_ui.state.UiState
import com.clearing.movies_domain.model.Language

@Immutable
data class MovieDetailsUiState(
    val uiState: UiState<Unit> = UiState.Loading,
    val id: Int = 0,
    val title: String = "",
    val posterPath: String = "",
    val overview: String = "",
    val releaseDate: String = "",
    val languages: List<Language> = emptyList(),
    val favoritesIds: List<Int> = emptyList(),
)
