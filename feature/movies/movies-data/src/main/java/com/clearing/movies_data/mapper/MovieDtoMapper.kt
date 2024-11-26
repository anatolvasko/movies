package com.clearing.movies_data.mapper

import com.clearing.core_ui.extention.orZero
import com.clearing.movies_data.remote.dto.MovieDto
import com.clearing.movies_domain.model.Movie

internal fun MovieDto.toMovie() : Movie {
    return Movie(
        id = id.orZero(),
        originalTitle = title.orEmpty(),
        posterPath = posterPath.orEmpty(),
    )
}