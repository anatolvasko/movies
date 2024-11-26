package com.clearing.movies_data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.clearing.movies_data.local.entity.MovieEntity

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies_table ")
    fun getMovies(): PagingSource<Int, MovieEntity >

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movies_table")
    suspend fun clearMovies()

}