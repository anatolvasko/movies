package com.clearing.movies_data.mapper

import com.clearing.movies_data.local.entity.SearchEntity
import com.clearing.movies_domain.model.Movie

fun SearchEntity.toMovie() : Movie {
    return Movie(
        id = id,
        originalTitle = originalTitle,
        posterPath = posterPath,
    )
}