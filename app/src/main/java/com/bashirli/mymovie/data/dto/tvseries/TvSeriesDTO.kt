package com.bashirli.mymovie.data.dto.tvseries


import com.google.gson.annotations.SerializedName

data class TvSeriesDTO(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val tvSeriesResults: List<TvSeriesResult>,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)