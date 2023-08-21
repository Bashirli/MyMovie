package com.bashirli.mymovie.domain.model.details.cast

import com.google.gson.annotations.SerializedName

data class CastModel(
    val adult: Boolean,
    val character: String,
    val id: Int,
    val name: String,
    val order: Int,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?
)