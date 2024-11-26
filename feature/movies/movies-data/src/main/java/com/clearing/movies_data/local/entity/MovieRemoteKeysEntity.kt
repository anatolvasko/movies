package com.clearing.movies_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.clearing.core_ui.Constants.MOVIE_KEYS_TABLE_NAME

@Entity(tableName = MOVIE_KEYS_TABLE_NAME)
data class MovieRemoteKeysEntity(
    @PrimaryKey(autoGenerate = true)
    val primaryId: Int = 0,
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
)