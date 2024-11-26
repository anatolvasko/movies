package com.clearing.movies_presentation.composable

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.clearing.core_ui.R
import com.clearing.core_ui.components.EmptyBox
import com.clearing.core_ui.components.ClearingErrorBox
import com.clearing.core_ui.components.ClearingTopAppBar
import com.clearing.core_ui.components.HideBottomSheetBox
import com.clearing.core_ui.components.LoadingBox
import com.clearing.core_ui.navigation.MoviesNavigation
import com.clearing.core_ui.state.UiState
import com.clearing.core_ui.theme.ClearingTheme
import com.clearing.core_ui.util.NavigationEvent
import com.clearing.core_ui.util.noRippleClickable
import com.clearing.movies_data.local.entity.MovieEntity
import com.clearing.movies_data.mapper.toMovie
import com.clearing.movies_domain.model.Movie
import com.clearing.movies_presentation.composable.components.MovieItem
import com.clearing.movies_presentation.composable.components.SearchBottomSheet
import com.clearing.movies_presentation.viewmodel.MovieViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    onNavigate: (NavigationEvent) -> Unit,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val movies = viewModel.moviesFlow.collectAsLazyPagingItems()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )
    var isOverlayVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(bottomSheetScaffoldState.bottomSheetState.targetValue) {
        isOverlayVisible =
            bottomSheetScaffoldState.bottomSheetState.targetValue == SheetValue.Expanded
    }

    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f),
                contentAlignment = Alignment.Center
            ) {
                SearchBottomSheet(
                    onItemClick = { movie ->
                        onNavigate(
                            NavigationEvent.Navigate(
                                route = MoviesNavigation.MovieDetailsRoute(
                                    id = movie.id,
                                    title = movie.originalTitle,
                                    posterPath = movie.posterPath
                                )
                            )
                        )
                    }
                )
            }
        },
        sheetPeekHeight = 0.dp,
        sheetSwipeEnabled = true,
        sheetContainerColor = ClearingTheme.colors.primaryColor,
        topBar = {
            ClearingTopAppBar(
                modifier = Modifier,
                title = {
                    Text(
                        text = stringResource(R.string.movies_title),
                        style = ClearingTheme.typography.text20.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                },
                actions = {
                    Icon(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 6.dp)
                            .size(28.dp)
                            .noRippleClickable {
                                viewModel.showOnlyFavorites(!state.showOnlyFavorites)
                            },
                        painter = painterResource(
                            id = if (state.showOnlyFavorites) R.drawable.ic_star_filled
                            else R.drawable.ic_star_outlined
                        ),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = null,
                    )
                    Icon(
                        modifier = Modifier
                            .padding(end = 12.dp, start = 6.dp)
                            .size(28.dp)
                            .noRippleClickable {
                                scope.launch {
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                }
                            },
                        imageVector = Icons.Default.Search,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = null,
                    )
                }
            )
        }
    ) { paddingValues ->

        when (state.uiState) {
            is UiState.Error -> {
                ClearingErrorBox()
            }

            UiState.Loading -> {
                LoadingBox()
            }

            is UiState.Success -> {

                if (movies.itemCount > 0) {
                    MoviesLazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        movies = movies,
                        favoritesId = state.favoritesIds,
                        onlyFavoritesMode = state.showOnlyFavorites,
                        onFavoritesClick = { id, addToFavorites ->
                            if (addToFavorites) viewModel.addToFavorites(id = id)
                            else viewModel.removeFromFavorites(id = id)

                        },
                        onItemClick = { movie ->
                            onNavigate(
                                NavigationEvent.Navigate(
                                    route = MoviesNavigation.MovieDetailsRoute(
                                        id = movie.id,
                                        title = movie.originalTitle,
                                        posterPath = movie.posterPath
                                    )
                                )
                            )
                        }
                    )
                } else {
                    EmptyBox()
                }

                HideBottomSheetBox(
                    isVisible = isOverlayVisible,
                    onClick = {
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.hide()
                        }
                    }
                )
            }
        }
    }

    BackHandler(enabled = bottomSheetScaffoldState.bottomSheetState.targetValue == SheetValue.Expanded) {
        scope.launch {
            bottomSheetScaffoldState.bottomSheetState.hide()
        }
    }
}

@Composable
private fun MoviesLazyColumn(
    modifier: Modifier = Modifier,
    onlyFavoritesMode: Boolean,
    favoritesId: List<Int>,
    movies: LazyPagingItems<MovieEntity>,
    onFavoritesClick: (Int, Boolean) -> Unit,
    onItemClick: (Movie) -> Unit
) {
    if (!onlyFavoritesMode) {
        LazyColumn(
            modifier = modifier
        ) {
            items(
                count = movies.itemCount,
                key = movies.itemKey { it.primaryId }
            ) { index ->

                val movie = movies[index]

                movie?.let {
                    val isFavorite = favoritesId.contains(movie.id)

                    MovieItem(
                        movie = movie.toMovie(),
                        isFavorite = isFavorite,
                        onFavoritesClick = { id, addToFavorites ->
                            onFavoritesClick(id, addToFavorites)
                        },
                        onItemClick = { movie ->
                            onItemClick(movie)
                        }
                    )
                }
            }
        }
    } else {

        val filteredMovies = remember(favoritesId) {
            movies.itemSnapshotList.items.filter { it.id in favoritesId }
        }

        if (filteredMovies.isNotEmpty()) {
            LazyColumn(
                modifier = modifier
            ) {
                items(filteredMovies.size) { index ->
                    val item = filteredMovies[index]

                    MovieItem(
                        movie = item.toMovie(),
                        isFavorite = true,
                        onFavoritesClick = { id, addToFavorites ->
                            onFavoritesClick(id, addToFavorites)
                        },
                        onItemClick = { movie ->
                            onItemClick(movie)
                        }
                    )
                }
            }
        } else {
            EmptyBox()
        }
    }
}

