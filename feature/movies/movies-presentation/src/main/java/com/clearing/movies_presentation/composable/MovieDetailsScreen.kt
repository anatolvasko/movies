package com.clearing.movies_presentation.composable

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.clearing.core_ui.Constants.BASE_URL_IMAGES
import com.clearing.core_ui.R
import com.clearing.core_ui.components.ClearingErrorBox
import com.clearing.core_ui.components.ClearingTopAppBar
import com.clearing.core_ui.components.HideBottomSheetBox
import com.clearing.core_ui.components.LoadingBox
import com.clearing.core_ui.extention.toReadableDate
import com.clearing.core_ui.navigation.MoviesNavigation
import com.clearing.core_ui.state.UiState
import com.clearing.core_ui.theme.ClearingTheme
import com.clearing.core_ui.theme.primary8
import com.clearing.core_ui.util.NavigationEvent
import com.clearing.core_ui.util.noRippleClickable
import com.clearing.movies_presentation.composable.components.ImageErrorPlaceHolder
import com.clearing.movies_presentation.composable.components.ImageLoadingPlaceHolder
import com.clearing.movies_presentation.composable.components.LanguagesBottomSheet
import com.clearing.movies_presentation.viewmodel.MovieDetailsViewModel
import kotlinx.coroutines.launch

private val posterHeight = 300.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    onNavigate: (NavigationEvent) -> Unit,
    args: MoviesNavigation.MovieDetailsRoute,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

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
            LanguagesBottomSheet(languages = state.languages.map { it.name })
        },
        sheetPeekHeight = 0.dp,
        sheetSwipeEnabled = true,
        sheetContainerColor = ClearingTheme.colors.primaryColor,
        topBar = {
            ClearingTopAppBar(
                modifier = Modifier,
                navigationIconVisible = true,
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 6.dp)
                            .size(28.dp)
                            .noRippleClickable {
                                onNavigate(
                                    NavigationEvent.NavigateUp
                                )
                            },
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = null,
                    )
                },
                title = {
                    Text(
                        text = state.title,
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
                                if (state.favoritesIds.contains(state.id)) viewModel.removeFromFavorites(
                                    state.id
                                )
                                else viewModel.addToFavorites(state.id)
                            },
                        painter = painterResource(
                            id = if (state.favoritesIds.contains(state.id)) R.drawable.ic_star_filled
                            else R.drawable.ic_star_outlined
                        ),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = null,
                    )
                }
            )
        }
    ) { paddings ->

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Poster(posterPath = state.posterPath)

            Spacer(modifier = Modifier.height(12.dp))

            when (state.uiState) {
                is UiState.Error -> {
                    ClearingErrorBox(text = stringResource(R.string.general_error))
                }

                UiState.Loading -> {
                    LoadingBox()
                }

                is UiState.Success -> {

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .fillMaxWidth(),
                        text = state.overview,
                        style = ClearingTheme.typography.default
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .fillMaxWidth(),
                        text = "${stringResource(R.string.movie_details_release_date)}: ${state.releaseDate.toReadableDate()}",
                        style = ClearingTheme.typography.default
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (state.languages.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .fillMaxWidth()
                                .noRippleClickable {
                                    scope.launch {
                                        bottomSheetScaffoldState.bottomSheetState.expand()
                                    }
                                },
                            text = stringResource(R.string.movie_details_languages),
                            style = ClearingTheme.typography.default,
                            textDecoration = TextDecoration.Underline,
                            color = primary8
                        )
                    }
                }
            }
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
    BackHandler(enabled = bottomSheetScaffoldState.bottomSheetState.targetValue == SheetValue.Expanded) {
        scope.launch {
            bottomSheetScaffoldState.bottomSheetState.hide()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.provideMovieArgs(
            id = args.id,
            title = args.title,
            posterPath = args.posterPath
        )

        viewModel.navigationFlow.collect { event ->
            when (event) {
                is MovieDetailsViewModel.Event.DetailsLoadingFailed -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.movie_details_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is MovieDetailsViewModel.Event.ReleaseDateLoadingFailed -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.movie_release_date_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

@Composable
private fun Poster(
    posterPath: String
) {
    val painter = rememberAsyncImagePainter(model = "$BASE_URL_IMAGES${posterPath}")

    if (painter.state is AsyncImagePainter.State.Loading) {
        ImageLoadingPlaceHolder(
            modifier = Modifier
                .height(posterHeight)
                .width(posterHeight * 2 / 3)
        )
    }

    if (painter.state is AsyncImagePainter.State.Error) {
        ImageErrorPlaceHolder(
            modifier = Modifier
                .height(posterHeight)
                .width(posterHeight * 2 / 3)
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(posterHeight),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .fillMaxHeight(),
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )
        }
    }
}