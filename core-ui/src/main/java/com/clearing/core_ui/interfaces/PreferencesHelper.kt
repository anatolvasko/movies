package com.clearing.core_ui.interfaces

import kotlinx.coroutines.flow.StateFlow

interface PreferencesHelper {

    val favoritesIds: StateFlow<List<Int>>

    fun saveFavoritesIds(key: String, values: List<Int>)

    fun getFavoritesIds(key: String): List<Int>
}