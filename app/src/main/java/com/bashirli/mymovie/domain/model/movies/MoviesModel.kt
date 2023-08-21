package com.bashirli.mymovie.domain.model.movies

data class MoviesModel(
    val page: Int,
    val results: List<ResultModel>
)