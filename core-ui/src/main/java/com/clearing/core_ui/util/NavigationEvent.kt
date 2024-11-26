package com.clearing.core_ui.util

import com.clearing.core_ui.navigation.MoviesNavigation
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationEvent {

    @Serializable
    data object NavigateUp : NavigationEvent()

    @Serializable
    data class Navigate(val route: MoviesNavigation) : NavigationEvent()

}