package com.clearing.movies_data.di

import android.content.Context
import androidx.room.Room
import com.clearing.core_ui.Constants.BASE_URL
import com.clearing.core_ui.Constants.DATABASE_NAME
import com.clearing.movies_data.local.MoviesDatabase
import com.clearing.movies_data.remote.MoviesApi
import com.clearing.movies_data.repository.MoviesRepository
import com.clearing.movies_data.repository.MoviesRepositoryImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesDataModule {

    @Provides
    @Singleton
    fun provideMoviesApi(httpClient: OkHttpClient, gson: Gson) : MoviesApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
            .build().create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesDatabase(@ApplicationContext context: Context) : MoviesDatabase  {
        return Room.databaseBuilder(
            context = context,
            klass = MoviesDatabase::class.java,
            name = DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(
        api: MoviesApi,
        database: MoviesDatabase
    ) : MoviesRepository {
        return MoviesRepositoryImpl(
            api = api,
            database = database,
        )
    }
}