package com.bashirli.mymovie.domain.model.details.tvseries

import com.google.gson.annotations.SerializedName

data class SeasonsModel(
    val airDate: String?,
    val episodeCount: Int?,
    val id: Int?,
    val name: String?,
    val overview: String?,
    val posterPath: String?,
    val seasonNumber: Int?,
    val voteAverage: Double?
)
