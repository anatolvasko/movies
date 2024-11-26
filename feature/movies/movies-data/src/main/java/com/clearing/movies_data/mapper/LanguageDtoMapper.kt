package com.clearing.movies_data.mapper

import com.clearing.movies_data.remote.dto.LanguageDto
import com.clearing.movies_domain.model.Language

internal fun LanguageDto.toLanguage() : Language {
    return Language(
        englishName = this.englishName.orEmpty(),
        iso = this.iso.orEmpty(),
        name = this.name.orEmpty(),
    )
}