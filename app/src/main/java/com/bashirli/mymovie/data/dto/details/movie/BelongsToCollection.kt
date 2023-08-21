package com.bashirli.mymovie.data.dto.details.movie


import com.google.gson.annotations.SerializedName

data class BelongsToCollection(
    @SerializedName("backdrop_path")
    val backdropPath: Any?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String?
)