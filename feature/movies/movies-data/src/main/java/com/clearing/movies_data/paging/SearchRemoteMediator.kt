package com.clearing.movies_data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.clearing.movies_data.local.MoviesDatabase
import com.clearing.movies_data.local.entity.SearchEntity
import com.clearing.movies_data.local.entity.SearchRemoteKeysEntity
import com.clearing.movies_data.mapper.toMovie
import com.clearing.movies_data.mapper.toSearchEntity
import com.clearing.movies_data.remote.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class SearchRemoteMediator(
    private val query: String,
    private val api: MoviesApi,
    private val database: MoviesDatabase
) : RemoteMediator<Int, SearchEntity>() {

    private val searchDao = database.searchDao
    private val keysDao = database.searchKeysDao

    override suspend fun initialize(): InitializeAction {
        return if (query.isBlank()) InitializeAction.SKIP_INITIAL_REFRESH
        else InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchEntity>
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

            val searchedMovies = withContext(Dispatchers.IO) {
                api.searchMovies(page = currentPage, query = query)
                    .results
                    .orEmpty()
                    .map { it.toMovie() }
            }

            val endOfPaginationReached = searchedMovies.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    searchDao.clearMovies()
                    keysDao.deleteAllRemoteKeys()
                }
                val keys = searchedMovies.map { movie ->
                    SearchRemoteKeysEntity(
                        id = movie.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                keysDao.addAllRemoteKeys(remoteKeys = keys)
                searchDao.addMovies(movies = searchedMovies.map { it.toSearchEntity() })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            searchDao.clearMovies()
            keysDao.deleteAllRemoteKeys()
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, SearchEntity>
    ): SearchRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                keysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, SearchEntity>
    ): SearchRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movieEntity ->
                keysDao.getRemoteKeys(id = movieEntity.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, SearchEntity>
    ): SearchRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movieEntity ->
                val remoteKEy = keysDao.getRemoteKeys(id = movieEntity.id)
                remoteKEy
            }
    }
}