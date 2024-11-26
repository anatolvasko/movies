package com.clearing.movies_data.remote.dto

import com.google.gson.annotations.SerializedName


data class MovieDetailsResponse(
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("spoken_languages") val results: List<LanguageDto>? = null,
)
