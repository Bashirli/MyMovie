package com.bashirli.mymovie.data.dto.celebs.tvseries


import com.google.gson.annotations.SerializedName

data class CelebTvSeriesDTO(
    @SerializedName("cast")
    val celebTvCast: List<CelebTvCast>,
    @SerializedName("crew")
    val celebTvCrew: List<CelebTvCrew>,
    @SerializedName("id")
    val id: Int?
)