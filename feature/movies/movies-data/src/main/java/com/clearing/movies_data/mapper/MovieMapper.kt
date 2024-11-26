package com.clearing.movies_data.mapper

import com.clearing.movies_data.local.entity.MovieEntity
import com.clearing.movies_data.local.entity.SearchEntity
import com.clearing.movies_domain.model.Movie

internal fun Movie.toMovieEntity() : MovieEntity {
    return MovieEntity(
        id = this.id,
        originalTitle = this.originalTitle,
        posterPath = this.posterPath,
    )
}

internal fun Movie.toSearchEntity() : SearchEntity {
    return SearchEntity(
        id = this.id,
        originalTitle = this.originalTitle,
        posterPath = this.posterPath,
    )
}