package com.clearing.movies_data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.clearing.movies_data.local.entity.SearchRemoteKeysEntity

@Dao
interface SearchRemoteKeysDao {

    @Query("SELECT * FROM search_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): SearchRemoteKeysEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<SearchRemoteKeysEntity>)

    @Query("DELETE FROM search_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}