package com.clearing.movies_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.clearing.core_ui.Constants.SEARCH_KEYS_TABLE_NAME

@Entity(tableName = SEARCH_KEYS_TABLE_NAME)
data class SearchRemoteKeysEntity(
    @PrimaryKey(autoGenerate = true)
    val primaryId: Int = 0,
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
)