package com.clearing.movies_presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.clearing.core_ui.Constants.EMPTY_STRING
import com.clearing.core_ui.interfaces.PreferencesHelper
import com.clearing.movies_data.repository.MoviesRepository
import com.clearing.movies_presentation.state.SearchUiState
import com.clearing.movies_presentation.util.FavoritesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val favoritesManager: FavoritesManager
) : ViewModel(){

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow(EMPTY_STRING)
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val searchMoviesFlow = _searchQuery
        .debounce(SEARCH_DELAY)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            repository.getPagingSearchMovies(query.trim())
                .cachedIn(viewModelScope)
        }

    init {
        viewModelScope.launch {
            _searchQuery.emit(_searchQuery.value)
            favoritesManager.getFavoritesFlow().collect { favoritesIds ->
                _uiState.update { it.copy(favoritesIds = favoritesIds) }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addToFavorites(id: Int) {
        favoritesManager.addToFavorites(id)
    }

    fun removeFromFavorites(id: Int){
        favoritesManager.removeFromFavorites(id)
    }

    companion object {
        private const val SEARCH_DELAY = 1000L
    }
}