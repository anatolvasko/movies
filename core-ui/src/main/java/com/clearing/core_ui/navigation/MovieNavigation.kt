package com.clearing.core_ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class MoviesNavigation {

    @Serializable
    data object MoviesRoute : MoviesNavigation()

    @Serializable
    class MovieDetailsRoute(
        val id: Int,
        val title: String,
        val posterPath: String,
    ) : MoviesNavigation()

}