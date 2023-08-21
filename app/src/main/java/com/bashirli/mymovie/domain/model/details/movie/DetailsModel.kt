package com.bashirli.mymovie.domain.model.details.movie

data class DetailsModel(
    val adult: Boolean,
    val backdropPath: String?,
    val belongsToCollection: BelongsModel?,
    val budget: Int,
    val genres: List<GenreDetailsModel>,
    val homepage: String,
    val id: Int,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompanyModel>,
    val productionCountries: List<ProductionCountryModel>,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    val spokenLanguages: List<SpokenLanguageModel>,
    val status: String,
    val tagline: String,
    val title: String,
    val video:Boolean,
    val voteAverage: Double,
    val voteCount: Int
)