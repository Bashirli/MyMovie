package com.bashirli.mymovie.domain.model.details.tvseries

import com.bashirli.mymovie.data.dto.details.tvseries.CreatedBy
import com.bashirli.mymovie.data.dto.details.tvseries.Genre
import com.bashirli.mymovie.data.dto.details.tvseries.LastEpisodeToAir
import com.bashirli.mymovie.data.dto.details.tvseries.Network
import com.bashirli.mymovie.data.dto.details.tvseries.NextEpisodeToAir
import com.bashirli.mymovie.data.dto.details.tvseries.ProductionCompany
import com.bashirli.mymovie.data.dto.details.tvseries.ProductionCountry
import com.bashirli.mymovie.data.dto.details.tvseries.Season
import com.bashirli.mymovie.data.dto.details.tvseries.SpokenLanguage
import com.bashirli.mymovie.domain.model.details.movie.GenreDetailsModel
import com.bashirli.mymovie.domain.model.details.movie.ProductionCompanyModel
import com.bashirli.mymovie.domain.model.details.movie.SpokenLanguageModel
import com.google.gson.annotations.SerializedName

data class TvSeriesDetailsModel(
    val adult: Boolean?,
    val backdropPath: String?,
    val firstAirDate: String?,
    val genres: List<GenreDetailsModel>,
    val id: Int?,
    val languages: List<String>,
    val name: String?,
    val numberOfEpisodes: Int?,
    val numberOfSeasons: Int?,
    val originCountry: List<String>,
    val originalLanguage: String?,
    val originalName: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompanyModel>,
    val seasons: List<SeasonsModel>,
    val spokenLanguages: List<SpokenLanguageModel>,
    val status: String?,
    val tagline: String?,
    val type: String?,
    val voteAverage: Double?,
    val voteCount: Int?
)