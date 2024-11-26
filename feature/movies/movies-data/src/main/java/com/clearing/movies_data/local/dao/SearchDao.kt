package com.clearing.movies_data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.clearing.movies_data.local.entity.SearchEntity

@Dao
interface SearchDao {

    @Query("SELECT * FROM search_table ")
    fun getMovies(): PagingSource<Int, SearchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<SearchEntity>)

    @Query("DELETE FROM search_table")
    suspend fun clearMovies()

}