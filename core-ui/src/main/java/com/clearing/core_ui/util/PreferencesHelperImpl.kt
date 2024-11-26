package com.clearing.core_ui.util

import android.content.SharedPreferences
import com.clearing.core_ui.Constants.EMPTY_STRING
import com.clearing.core_ui.Constants.PREFERENCES_NAME
import com.clearing.core_ui.interfaces.PreferencesHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PreferencesHelperImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PreferencesHelper {

    private val _favoritesIds = MutableStateFlow(loadFavoritesIds())
    override val favoritesIds: StateFlow<List<Int>> get() = _favoritesIds

    override fun saveFavoritesIds(key: String, values: List<Int>) {
        val serialized = Json.encodeToString(values)
        sharedPreferences.edit().putString(key, serialized).apply()
        _favoritesIds.update { values }
    }

    override fun getFavoritesIds(key: String): List<Int> {
        return _favoritesIds.value
    }

    private fun loadFavoritesIds(): List<Int> {
        return try {
            val serialized = sharedPreferences.getString(PREFERENCES_NAME, EMPTY_STRING).orEmpty()
            Json.decodeFromString(serialized)
        } catch (e: SerializationException) {
            emptyList()
        }
    }
}