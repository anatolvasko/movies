package com.clearing.movies_presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.clearing.core_ui.Constants.UNKNOWN_ERROR
import com.clearing.core_ui.state.UiState
import com.clearing.movies_data.repository.MoviesRepository
import com.clearing.movies_presentation.state.MoviesUiState
import com.clearing.movies_presentation.util.FavoritesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    repository: MoviesRepository,
    private val favoritesManager: FavoritesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    val moviesFlow = repository.getPagingMovies()
        .cachedIn(viewModelScope)

    init {
        _uiState.update { it.copy(uiState = UiState.Loading) }
        observeMovies()
        viewModelScope.launch {
            favoritesManager.getFavoritesFlow().collect { favoritesIds ->
                _uiState.update { it.copy(favoritesIds = favoritesIds) }
            }
        }
    }

    private fun observeMovies() {
        moviesFlow
            .onEach {
                _uiState.update { it.copy(uiState = UiState.Success(Unit)) }
            }
            .catch { throwable ->
                _uiState.update { it.copy(uiState = UiState.Error(throwable.message ?: UNKNOWN_ERROR)) }
            }
            .launchIn(viewModelScope)
    }

    fun addToFavorites(id: Int) {
        favoritesManager.addToFavorites(id)
    }

    fun removeFromFavorites(id: Int){
        favoritesManager.removeFromFavorites(id)
    }

    fun showOnlyFavorites(value: Boolean) {
        _uiState.update { it.copy(showOnlyFavorites = value) }
    }

}