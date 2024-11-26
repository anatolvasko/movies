package com.clearing.movies_data.mapper

import com.clearing.movies_data.remote.dto.MovieDetailsResponse
import com.clearing.movies_domain.model.MovieDetails

internal fun MovieDetailsResponse.toMovieDetails() : MovieDetails {
    return MovieDetails(
        overview = this.overview.orEmpty(),
        languages = results?.map { it.toLanguage()}.orEmpty()
    )
}