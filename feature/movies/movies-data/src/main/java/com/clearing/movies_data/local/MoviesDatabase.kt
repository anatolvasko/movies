package com.clearing.movies_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.clearing.movies_data.local.dao.MovieRemoteKeysDao
import com.clearing.movies_data.local.dao.MoviesDao
import com.clearing.movies_data.local.dao.SearchDao
import com.clearing.movies_data.local.dao.SearchRemoteKeysDao
import com.clearing.movies_data.local.entity.MovieEntity
import com.clearing.movies_data.local.entity.MovieRemoteKeysEntity
import com.clearing.movies_data.local.entity.SearchEntity
import com.clearing.movies_data.local.entity.SearchRemoteKeysEntity

@Database(
    entities = [
        MovieEntity::class,
        MovieRemoteKeysEntity::class,
        SearchEntity::class,
        SearchRemoteKeysEntity::class,
    ],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract val moviesDao: MoviesDao

    abstract val moviesKeysDao: MovieRemoteKeysDao

    abstract val searchDao: SearchDao

    abstract val searchKeysDao: SearchRemoteKeysDao

}