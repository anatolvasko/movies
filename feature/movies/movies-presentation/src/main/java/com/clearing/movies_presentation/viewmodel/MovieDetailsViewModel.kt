package com.clearing.movies_presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clearing.core_ui.Constants.UNKNOWN_ERROR
import com.clearing.core_ui.state.UiState
import com.clearing.movies_data.repository.MoviesRepository
import com.clearing.movies_presentation.state.MovieDetailsUiState
import com.clearing.movies_presentation.util.FavoritesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val favoritesManager: FavoritesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailsUiState())
    val uiState: StateFlow<MovieDetailsUiState> = _uiState.asStateFlow()

    private val navigationChannel = Channel<Event>()
    val navigationFlow = navigationChannel.receiveAsFlow()

    fun provideMovieArgs(id: Int, title: String, posterPath: String) {
        _uiState.update {
            it.copy(
                id = id,
                title = title,
                posterPath = posterPath,
            )
        }
        loadMovieDetails()
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch {
            favoritesManager.getFavoritesFlow().collect { favoritesIds ->
                _uiState.update { it.copy(favoritesIds = favoritesIds) }
            }
        }
    }

    private fun loadMovieDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            val id = _uiState.value.id

            val movieDetailsDeferred = async { repository.getMovieDetails(id = id) }
            val movieReleaseDateDeferred = async { repository.getMovieReleaseDate(id = id) }

            val movieDetailsResult = movieDetailsDeferred.await()
            val movieReleaseDateResult = movieReleaseDateDeferred.await()

            withContext(Dispatchers.Main) {
                movieDetailsResult
                    .onSuccess { movieDetails ->
                        _uiState.update {
                            it.copy(
                                overview = movieDetails.overview,
                                languages = movieDetails.languages
                            )
                        }
                    }
                    .onFailure {
                        navigationChannel.send(Event.DetailsLoadingFailed)
                    }

                movieReleaseDateResult
                    .onSuccess { movieReleaseDate ->
                        _uiState.update { it.copy(releaseDate = movieReleaseDate.releaseDate) }
                    }
                    .onFailure {
                        navigationChannel.send(Event.ReleaseDateLoadingFailed)
                    }


                if (movieDetailsResult.isSuccess && movieReleaseDateResult.isSuccess) {
                    _uiState.update { it.copy(uiState = UiState.Success(Unit)) }
                } else {
                    _uiState.update { it.copy(uiState = UiState.Error(UNKNOWN_ERROR)) }
                }
            }
        }
    }

    fun addToFavorites(id: Int) {
        favoritesManager.addToFavorites(id)
    }

    fun removeFromFavorites(id: Int){
        favoritesManager.removeFromFavorites(id)
    }

    sealed interface Event {
        data object DetailsLoadingFailed : Event
        data object ReleaseDateLoadingFailed : Event
    }
}