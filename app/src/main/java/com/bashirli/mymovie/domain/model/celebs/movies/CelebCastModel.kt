package com.bashirli.mymovie.domain.model.celebs.movies

import com.google.gson.annotations.SerializedName

data class CelebCastModel(
    val adult: Boolean?,
    val backdropPath: String?,
    val character: String?,
    val creditId: String?,
    val genreIds: List<Int?>?,
    val id: Int?,
    val order: Int?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?
)
