package com.clearing.movies_data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.clearing.movies_data.local.entity.MovieRemoteKeysEntity

@Dao
interface MovieRemoteKeysDao {

    @Query("SELECT * FROM movie_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): MovieRemoteKeysEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<MovieRemoteKeysEntity>)

    @Query("DELETE FROM movie_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

}
