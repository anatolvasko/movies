package com.clearing.movies_presentation.di

import com.clearing.core_ui.interfaces.PreferencesHelper
import com.clearing.movies_presentation.util.FavoritesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesPresentationModule {

    @Singleton
    @Provides
    fun provideFavoritesManager(preferencesHelper: PreferencesHelper) : FavoritesManager {
        return FavoritesManager(preferencesHelper = preferencesHelper)
    }

}