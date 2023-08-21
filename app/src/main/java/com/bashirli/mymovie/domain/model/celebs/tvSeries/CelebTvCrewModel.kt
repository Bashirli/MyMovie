package com.bashirli.mymovie.domain.model.celebs.tvSeries

import com.google.gson.annotations.SerializedName

data class CelebTvCrewModel(
    val adult: Boolean?,
    val backdropPath: String?,
    val creditId: String?,
    val department: String?,
    val episodeCount: Int?,
    val firstAirDate: String?,
    val genreIds: List<Int?>?,
    val id: Int?,
    val job: String?,
    val name: String?,
    val originCountry: List<String?>?,
    val originalLanguage: String?,
    val originalName: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val voteAverage: Double?,
    val voteCount: Int?
)
