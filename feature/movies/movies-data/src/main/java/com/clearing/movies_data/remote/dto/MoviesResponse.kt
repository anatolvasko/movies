package com.clearing.movies_data.remote.dto

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("results") val results: List<MovieDto>? = null,
    @SerializedName("total_pages") val totalPages: Int? = null,
    @SerializedName("total_results") val totalResults: Int? = null,
)