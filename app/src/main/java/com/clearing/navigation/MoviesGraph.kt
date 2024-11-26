package com.clearing.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.clearing.core_ui.navigation.MoviesNavigation
import com.clearing.core_ui.navigation.extension.navigate
import com.clearing.movies_presentation.composable.MovieDetailsScreen
import com.clearing.movies_presentation.composable.MoviesScreen

fun NavGraphBuilder.moviesGraph(
    navController: NavHostController,
) {

    composable<MoviesNavigation.MoviesRoute> {
        MoviesScreen(
            onNavigate = { event ->
                navController.navigate(event = event)
            }
        )
    }

    composable<MoviesNavigation.MovieDetailsRoute> {
        val args = it.toRoute<MoviesNavigation.MovieDetailsRoute>()
        MovieDetailsScreen(
            args = args,
            onNavigate = { event ->
                navController.navigate(event = event)
            }
        )
    }

}