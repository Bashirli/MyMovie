package com.bashirli.mymovie.data.dto.details.cast


import com.google.gson.annotations.SerializedName

data class CastDTO(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)