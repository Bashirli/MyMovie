package com.bashirli.mymovie.domain.model.tvseries

data class TvSeriesModel(
    val page: Int?,
    val tvSeriesResults: List<TvSeriesResultModel>,
    val totalPages: Int?,
    val totalResults: Int?
)
