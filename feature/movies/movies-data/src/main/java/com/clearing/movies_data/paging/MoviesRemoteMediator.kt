package com.clearing.movies_data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.clearing.movies_data.local.MoviesDatabase
import com.clearing.movies_data.local.entity.MovieEntity
import com.clearing.movies_data.local.entity.MovieRemoteKeysEntity
import com.clearing.movies_data.mapper.toMovie
import com.clearing.movies_data.mapper.toMovieEntity
import com.clearing.movies_data.remote.MoviesApi
import com.clearing.movies_domain.model.SortingBy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val api: MoviesApi,
    private val database: MoviesDatabase
) : RemoteMediator<Int, MovieEntity>() {

    private val moviesDao = database.moviesDao
    private val keysDao = database.moviesKeysDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )

                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val movies = withContext(Dispatchers.IO) {
                api.getMovies(page = currentPage, sorting = SortingBy.PopularityDesc.value)
                    .results
                    .orEmpty()
                    .map { it.toMovie() }
            }

            val endOfPaginationReached = movies.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    moviesDao.clearMovies()
                    keysDao.deleteAllRemoteKeys()
                }
                val keys = movies.map { movie ->
                    MovieRemoteKeysEntity(
                        id = movie.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                keysDao.addAllRemoteKeys(remoteKeys = keys)
                moviesDao.addMovies(movies = movies.map { it.toMovieEntity() })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ): MovieRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                keysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieEntity>
    ): MovieRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movieEntity ->
                keysDao.getRemoteKeys(id = movieEntity.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieEntity>
    ): MovieRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movieEntity ->
                val remoteKEy = keysDao.getRemoteKeys(id = movieEntity.id)
                remoteKEy
            }
    }
}