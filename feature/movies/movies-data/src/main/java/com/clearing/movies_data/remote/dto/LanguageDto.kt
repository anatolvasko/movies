package com.clearing.movies_data.remote.dto

import com.google.gson.annotations.SerializedName

data class LanguageDto(
    @SerializedName("english_name") val englishName: String? = null,
    @SerializedName("iso_639_1") val iso: String? = null,
    @SerializedName("name") val name: String? = null,
)
