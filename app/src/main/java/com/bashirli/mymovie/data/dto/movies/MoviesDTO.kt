package com.bashirli.mymovie.data.dto.movies


import com.google.gson.annotations.SerializedName

data class MoviesDTO(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)