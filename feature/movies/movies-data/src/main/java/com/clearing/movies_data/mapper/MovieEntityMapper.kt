package com.clearing.movies_data.mapper

import com.clearing.movies_data.local.entity.MovieEntity
import com.clearing.movies_domain.model.Movie

fun MovieEntity.toMovie() : Movie {
    return Movie(
        id = id,
        originalTitle = originalTitle,
        posterPath = posterPath,
    )
}