package com.clearing.movies_domain.model

data class MovieDetails(
    val overview: String,
    val languages: List<Language>,
)
