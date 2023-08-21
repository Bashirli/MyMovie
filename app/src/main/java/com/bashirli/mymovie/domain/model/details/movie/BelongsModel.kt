package com.bashirli.mymovie.domain.model.details.movie

import com.google.gson.annotations.SerializedName

data class BelongsModel (
    val backdropPath: Any?,
    val id: Int,
    val name: String,
    val posterPath: String?
        )