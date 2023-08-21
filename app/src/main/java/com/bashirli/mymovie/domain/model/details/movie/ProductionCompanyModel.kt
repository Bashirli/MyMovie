package com.bashirli.mymovie.domain.model.details.movie

import com.google.gson.annotations.SerializedName

data class ProductionCompanyModel(
    val id: Int?,
    val logoPath: String?,
    val name: String?,
    val originCountry: String?
)