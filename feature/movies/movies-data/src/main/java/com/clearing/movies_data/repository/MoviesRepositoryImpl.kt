package com.clearing.movies_data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.clearing.core_ui.Constants.PAGE_SIZE
import com.clearing.movies_data.local.MoviesDatabase
import com.clearing.movies_data.local.entity.MovieEntity
import com.clearing.movies_data.local.entity.SearchEntity
import com.clearing.movies_data.mapper.toMovieDetails
import com.clearing.movies_data.mapper.toMovieReleaseDate
import com.clearing.movies_data.paging.MoviesRemoteMediator
import com.clearing.movies_data.paging.SearchRemoteMediator
import com.clearing.movies_data.remote.MoviesApi
import com.clearing.movies_domain.model.MovieDetails
import com.clearing.movies_domain.model.MovieReleaseDate
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class MoviesRepositoryImpl(
    private val api: MoviesApi,
    private val database: MoviesDatabase
) : MoviesRepository {

    override fun getPagingMovies(): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize =  PAGE_SIZE,
                prefetchDistance = PAGE_SIZE/2,
                initialLoadSize = PAGE_SIZE
            ),
            remoteMediator = MoviesRemoteMediator(
                api = api,
                database = database
            ),
            pagingSourceFactory = {
                database.moviesDao.getMovies()
            }
        ).flow
    }

    override fun getPagingSearchMovies(query: String): Flow<PagingData<SearchEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize =  PAGE_SIZE,
                prefetchDistance = PAGE_SIZE/2,
                initialLoadSize = PAGE_SIZE,
            ),
            remoteMediator = SearchRemoteMediator(
                api = api,
                database = database,
                query = query
            ),
            pagingSourceFactory = {
                database.searchDao.getMovies()
            },
        ).flow
    }

    override suspend fun getMovieDetails(id: Int): Result<MovieDetails>  {
        return try {
            val result = api.getMovieDetails(movieId = id)
            Result.success(result.toMovieDetails())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMovieReleaseDate(id: Int): Result<MovieReleaseDate>  {
        return try {
            val result = api.getMovieReleaseDate(movieId = id)
            Result.success(result.toMovieReleaseDate())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}