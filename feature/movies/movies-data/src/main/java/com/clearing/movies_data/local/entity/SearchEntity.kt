package com.clearing.movies_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.clearing.core_ui.Constants.SEARCH_TABLE_NAME

@Entity(tableName = SEARCH_TABLE_NAME)
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    val primaryId: Int = 0,
    val id: Int,
    val originalTitle: String,
    val posterPath: String,
)
