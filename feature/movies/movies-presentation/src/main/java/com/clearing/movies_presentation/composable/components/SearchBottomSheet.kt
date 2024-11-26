package com.clearing.movies_presentation.composable.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.clearing.core_ui.R
import com.clearing.core_ui.components.EmptyBox
import com.clearing.core_ui.components.ClearingTextField
import com.clearing.movies_data.mapper.toMovie
import com.clearing.movies_domain.model.Movie
import com.clearing.movies_presentation.viewmodel.SearchViewModel

@Composable
fun SearchBottomSheet(
    viewModel: SearchViewModel = hiltViewModel(),
    onItemClick: (Movie) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) { paddingValues ->

        val searchQuery by viewModel.searchQuery.collectAsState()
        val state by viewModel.uiState.collectAsState()
        val searchedMovies = viewModel.searchMoviesFlow.collectAsLazyPagingItems()

        Column(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .fillMaxSize(),
        ) {
            ClearingTextField(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                value = searchQuery,
                onValueChange = { newValue ->
                    viewModel.updateSearchQuery(query = newValue)
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.search_placeholder))
                },
            )
            if (searchedMovies.itemCount > 0) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(
                        count = searchedMovies.itemCount,
                        key = searchedMovies.itemKey { it.primaryId }

                    ) { index ->

                        val movie = searchedMovies[index]

                        movie?.let {
                            val isFavorite = state.favoritesIds.contains(movie.id)

                            MovieItem(
                                movie = movie.toMovie(),
                                isFavorite = isFavorite,
                                height = 120.dp,
                                onFavoritesClick = { id, addToFavorites ->
                                    if (addToFavorites) viewModel.addToFavorites(id = id)
                                    else viewModel.removeFromFavorites(id = id)
                                },
                                onItemClick = { movie ->
                                    onItemClick(movie)
                                }
                            )
                        }
                    }
                }
            } else {
                EmptyBox()
            }
        }
    }
}