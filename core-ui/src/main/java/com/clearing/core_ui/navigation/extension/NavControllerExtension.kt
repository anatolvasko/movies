package com.clearing.core_ui.navigation.extension

import androidx.navigation.NavController
import com.clearing.core_ui.util.NavigationEvent

fun NavController.navigate(event: NavigationEvent) {
    when (event) {
        is NavigationEvent.Navigate -> {
            this.navigate(route = event.route)
        }

        NavigationEvent.NavigateUp -> {
            this.navigateUp()
        }
    }
}