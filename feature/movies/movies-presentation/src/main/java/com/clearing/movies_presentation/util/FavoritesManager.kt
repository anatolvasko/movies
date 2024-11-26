package com.clearing.movies_presentation.util

import com.clearing.core_ui.Constants.PREFERENCES_NAME
import com.clearing.core_ui.interfaces.PreferencesHelper
import kotlinx.coroutines.flow.StateFlow

class FavoritesManager(private val preferencesHelper: PreferencesHelper) {

    fun addToFavorites(id: Int) {
        val currentFavorites = preferencesHelper.getFavoritesIds(PREFERENCES_NAME)
        val updatedFavorites = currentFavorites + id
        preferencesHelper.saveFavoritesIds(PREFERENCES_NAME, updatedFavorites)
    }

    fun removeFromFavorites(id: Int) {
        val currentFavorites = preferencesHelper.getFavoritesIds(PREFERENCES_NAME)
        val updatedFavorites = currentFavorites - id
        preferencesHelper.saveFavoritesIds(PREFERENCES_NAME, updatedFavorites)
    }

    fun getFavoritesFlow(): StateFlow<List<Int>> {
        return preferencesHelper.favoritesIds
    }
}