package com.clearing.movies_data.repository

import androidx.paging.PagingData
import com.clearing.movies_data.local.entity.MovieEntity
import com.clearing.movies_data.local.entity.SearchEntity
import com.clearing.movies_domain.model.MovieDetails
import com.clearing.movies_domain.model.MovieReleaseDate
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getPagingMovies() : Flow<PagingData<MovieEntity>>

    fun getPagingSearchMovies(query: String) : Flow<PagingData<SearchEntity>>

    suspend fun getMovieDetails(id: Int) : Result<MovieDetails>

    suspend fun getMovieReleaseDate(id: Int) : Result<MovieReleaseDate>

}