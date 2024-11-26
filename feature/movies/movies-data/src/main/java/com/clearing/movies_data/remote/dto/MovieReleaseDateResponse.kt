package com.clearing.movies_data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieReleaseDateResponse(
    @SerializedName("release_date") val releaseDate: String? = null,
)
