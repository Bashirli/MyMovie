package com.bashirli.mymovie.data.dto.celebs.movies


import com.google.gson.annotations.SerializedName

data class CelebMoviesDTO(
    @SerializedName("cast")
    val celebCast: List<CelebCast>,
    @SerializedName("crew")
    val celebCrew: List<CelebCrew>,
    @SerializedName("id")
    val id: Int?
)