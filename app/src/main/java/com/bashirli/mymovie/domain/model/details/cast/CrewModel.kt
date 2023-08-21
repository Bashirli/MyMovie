package com.bashirli.mymovie.domain.model.details.cast

import com.google.gson.annotations.SerializedName

data class CrewModel(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?
)