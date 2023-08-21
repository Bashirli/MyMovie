package com.bashirli.mymovie.data.dto.celebs


import com.google.gson.annotations.SerializedName

data class CelebsDTO(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val celebsResults: List<CelebsResult>,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)