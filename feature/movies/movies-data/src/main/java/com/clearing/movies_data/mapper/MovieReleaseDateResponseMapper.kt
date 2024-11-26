package com.clearing.movies_data.mapper

import com.clearing.movies_data.remote.dto.MovieReleaseDateResponse
import com.clearing.movies_domain.model.MovieReleaseDate

internal fun MovieReleaseDateResponse.toMovieReleaseDate() : MovieReleaseDate {
    return MovieReleaseDate(
        releaseDate = this.releaseDate.orEmpty()
    )
}