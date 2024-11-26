package com.clearing.core_ui.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.clearing.core_ui.Constants.PREFERENCES_NAME
import com.clearing.core_ui.interfaces.PreferencesHelper
import com.clearing.core_ui.util.PreferencesHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    @Singleton
    fun provideAppContext(application: Application): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun providePreferencesHelper(sharedPreferences: SharedPreferences) : PreferencesHelper {
        return PreferencesHelperImpl(sharedPreferences)
    }

}