package com.bashirli.mymovie.data.mapper

import com.bashirli.mymovie.data.dto.category.Genre
import com.bashirli.mymovie.data.dto.category.GenreDTO
import com.bashirli.mymovie.data.dto.celebs.CelebsDTO
import com.bashirli.mymovie.data.dto.celebs.CelebsResult
import com.bashirli.mymovie.data.dto.celebs.details.CelebDetailsDTO
import com.bashirli.mymovie.data.dto.celebs.movies.CelebCast
import com.bashirli.mymovie.data.dto.celebs.movies.CelebCrew
import com.bashirli.mymovie.data.dto.celebs.movies.CelebMoviesDTO
import com.bashirli.mymovie.data.dto.celebs.tvseries.CelebTvCast
import com.bashirli.mymovie.data.dto.celebs.tvseries.CelebTvCrew
import com.bashirli.mymovie.data.dto.celebs.tvseries.CelebTvSeriesDTO
import com.bashirli.mymovie.data.dto.details.cast.Cast
import com.bashirli.mymovie.data.dto.details.cast.CastDTO
import com.bashirli.mymovie.data.dto.details.cast.Crew
import com.bashirli.mymovie.data.dto.details.images.Backdrop
import com.bashirli.mymovie.data.dto.details.images.ImagesDTO
import com.bashirli.mymovie.data.dto.details.movie.BelongsToCollection
import com.bashirli.mymovie.data.dto.details.movie.DetailsDTO
import com.bashirli.mymovie.data.dto.details.movie.ProductionCompany
import com.bashirli.mymovie.data.dto.details.movie.ProductionCountry
import com.bashirli.mymovie.data.dto.details.movie.SpokenLanguage
import com.bashirli.mymovie.data.dto.details.reviews.AuthorDetails
import com.bashirli.mymovie.data.dto.details.reviews.ReviewDTO
import com.bashirli.mymovie.data.dto.details.tvseries.Season
import com.bashirli.mymovie.data.dto.details.tvseries.TvSeriesDetailsDTO
import com.bashirli.mymovie.data.dto.local.FavoritesDTO
import com.bashirli.mymovie.data.dto.movies.MoviesDTO
import com.bashirli.mymovie.data.dto.movies.Result
import com.bashirli.mymovie.data.dto.tvseries.TvSeriesDTO
import com.bashirli.mymovie.data.dto.tvseries.TvSeriesResult
import com.bashirli.mymovie.domain.model.celebs.CelebsModel
import com.bashirli.mymovie.domain.model.celebs.CelebsResultModel
import com.bashirli.mymovie.domain.model.celebs.detail.CelebDetailsModel
import com.bashirli.mymovie.domain.model.celebs.movies.CelebCastModel
import com.bashirli.mymovie.domain.model.celebs.movies.CelebCrewModel
import com.bashirli.mymovie.domain.model.celebs.movies.CelebMoviesModel
import com.bashirli.mymovie.domain.model.celebs.tvSeries.CelebTvCastModel
import com.bashirli.mymovie.domain.model.celebs.tvSeries.CelebTvCrewModel
import com.bashirli.mymovie.domain.model.celebs.tvSeries.CelebTvSeriesModel
import com.bashirli.mymovie.domain.model.details.cast.CastBaseModel
import com.bashirli.mymovie.domain.model.details.cast.CastModel
import com.bashirli.mymovie.domain.model.details.cast.CrewModel
import com.bashirli.mymovie.domain.model.details.images.BackdropModel
import com.bashirli.mymovie.domain.model.details.images.ImagesModel
import com.bashirli.mymovie.domain.model.details.movie.BelongsModel
import com.bashirli.mymovie.domain.model.details.movie.DetailsModel
import com.bashirli.mymovie.domain.model.details.movie.GenreDetailsModel
import com.bashirli.mymovie.domain.model.details.movie.ProductionCompanyModel
import com.bashirli.mymovie.domain.model.details.movie.ProductionCountryModel
import com.bashirli.mymovie.domain.model.details.movie.SpokenLanguageModel
import com.bashirli.mymovie.domain.model.details.reviews.AuthorModel
import com.bashirli.mymovie.domain.model.details.reviews.ReviewModel
import com.bashirli.mymovie.domain.model.details.tvseries.SeasonsModel
import com.bashirli.mymovie.domain.model.details.tvseries.TvSeriesDetailsModel
import com.bashirli.mymovie.domain.model.local.FavoritesModel
import com.bashirli.mymovie.domain.model.movies.GenreBaseModel
import com.bashirli.mymovie.domain.model.movies.GenreModel
import com.bashirli.mymovie.domain.model.movies.MoviesModel
import com.bashirli.mymovie.domain.model.movies.ResultModel
import com.bashirli.mymovie.domain.model.tvseries.TvSeriesModel
import com.bashirli.mymovie.domain.model.tvseries.TvSeriesResultModel

fun MoviesDTO.toMoviesModel()=MoviesModel(
    page = page,
    results = results.toResultModel()
)

fun List<Result>.toResultModel()= map {
    with(it){
        ResultModel(
            adult,
            backdropPath,
            genreIds,
            id,
            originalLanguage,
            originalTitle,
            overview,
            popularity,
            posterPath,
            releaseDate,
            title,
            video,
            voteAverage,
            voteCount
        )
    }
}

fun Result.toResultModel()=  ResultModel(
    adult,
    backdropPath,
    genreIds,
    id,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount
)

fun GenreDTO.toGenreBaseModel()=GenreBaseModel(
    genres = genres.toGenreModel()
)


@JvmName("Model")
fun List<Genre>.toGenreModel()=map {
    GenreModel(
        id = it.id,
        name = it.name
    )
}

fun BelongsToCollection.toBelongsToModel()= BelongsModel(
    id = id,
    backdropPath = backdropPath,
    name = name,
    posterPath = posterPath
)

fun List<SpokenLanguage>.toSpokenLanguageModel()=map {
    with(it){
        SpokenLanguageModel(
            englishName, iso6391, name
        )
    }
}

fun List<ProductionCountry>.toProductionCountryModel()=map {
    with(it){
        ProductionCountryModel(
            iso31661, name
        )
    }
}


fun List<ProductionCompany>.toProductionCompanyModel()=map {
    with(it){
        ProductionCompanyModel(
            id, logoPath, name, originCountry
        )
    }
}

fun List<com.bashirli.mymovie.data.dto.details.movie.Genre>.toGenreDetailsModel()=map {
    with(it){
        GenreDetailsModel(
            id, name
        )
    }
}

fun DetailsDTO.toDetailsModel()= DetailsModel(
    adult,
    backdropPath,
    belongsToCollection?.toBelongsToModel(),
    budget,
    genres.toGenreDetailsModel(),
    homepage,
    id,
    imdbId,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    productionCompanies.toProductionCompanyModel(),
    productionCountries.toProductionCountryModel(),
    releaseDate,
    revenue,
    runtime,
    spokenLanguages.toSpokenLanguageModel(),
    status,
    tagline,
    title,
    video,
    voteAverage,
    voteCount
)


fun List<Cast>.toCastModel()=map{
    with(it){
        CastModel(
            adult, character, id, name, order, originalName, popularity, profilePath
        )
    }
}

fun List<Crew>.toCrewModel()=map {
    with(it){
        CrewModel(
            adult, gender, id, name, originalName, popularity, profilePath
        )
    }
}

fun CastDTO.toCastBaseModel()=CastBaseModel(
    cast = cast.toCastModel(),
    crew = crew.toCrewModel(),
    id = id
)


fun List<Backdrop>.toBackdropModel()=map {
    with(it){
        BackdropModel(
            filePath
        )
    }
}

fun ImagesDTO.toImagesModel()=ImagesModel(
    backdrops.toBackdropModel(),
    id
)

fun AuthorDetails.toAutherModel()=AuthorModel(
    avatarPath, name, rating, username
)

fun List<com.bashirli.mymovie.data.dto.details.reviews.Result>.toResultReviewModel()=map {
    with(it){
        com.bashirli.mymovie.domain.model.details.reviews.ResultModel(
            author,authorDetails.toAutherModel(),content, createdAt, id, updatedAt, url
        )
    }
}

fun ReviewDTO.toReviewModel()=ReviewModel(
    id, page, results.toResultReviewModel(), totalPages, totalResults
)

fun List<CelebsResult>.toCelebsResultModelList()=map {
    with(it){
        CelebsResultModel(
            adult, gender, id, knownForDepartment, name, popularity, profilePath
        )
    }
}

fun CelebsResult.toCelebsResultModel()=
    CelebsResultModel(
            adult, gender, id, knownForDepartment, name, popularity, profilePath
        )

fun CelebsDTO.toCelebsModel()=CelebsModel(
    page = page,
    celebsResults = celebsResults.toCelebsResultModelList()
)

fun CelebDetailsDTO.toCelebDetailsModel()=CelebDetailsModel(
    adult,
    alsoKnownAs,
    biography,
    birthday,
    deathday,
    gender,
    homepage,
    id,
    knownForDepartment,
    name,
    placeOfBirth,
    popularity,
    profilePath
)

fun List<CelebCrew>.toCelebCrewModel()=map {
    with(it){
        CelebCrewModel(
            adult, backdropPath, creditId, department, genreIds, id, job, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, title, video, voteAverage, voteCount
        )
    }
}

fun List<CelebCast>.toCelebCastModel()=map {
    with(it){
        CelebCastModel(
            adult, backdropPath, character, creditId, genreIds, id, order, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, title, video, voteAverage, voteCount
        )
    }
}

fun CelebMoviesDTO.toCelebMoviesModel()=CelebMoviesModel(
    celebCast = celebCast.toCelebCastModel(),
    celebCrew = celebCrew.toCelebCrewModel(),
    id = id
)

fun List<CelebTvCast>.toCelebTvCastModel()=map {
    with(it){
        CelebTvCastModel(
            adult, backdropPath, character, creditId, episodeCount, firstAirDate, genreIds, id, name, originCountry, originalLanguage, originalName, overview, popularity, posterPath, voteAverage, voteCount
        )
    }
}

fun List<CelebTvCrew>.toCelebTvCrew()=map {
    with(it){
        CelebTvCrewModel(
            adult,
            backdropPath,
            creditId,
            department,
            episodeCount,
            firstAirDate,
            genreIds,
            id,
            job,
            name,
            originCountry,
            originalLanguage,
            originalName,
            overview,
            popularity,
            posterPath,
            voteAverage,
            voteCount
        )
    }
}

fun CelebTvSeriesDTO.toCelebTvSeriesModel() = CelebTvSeriesModel(
    id = id,
    celebTvCast = celebTvCast.toCelebTvCastModel(),
    celebTvCrew = celebTvCrew.toCelebTvCrew()
)

fun List<TvSeriesResult>.toTvSeriesResultModel() = map {
    with(it) {
        TvSeriesResultModel(
            backdropPath,
            firstAirDate,
            genreIds,
            id,
            name,
            originCountry,
            originalLanguage,
            originalName,
            overview,
            popularity,
            posterPath,
            voteAverage,
            voteCount
        )
    }
}

fun TvSeriesResult.toTvSeriesResultModel()= TvSeriesResultModel(
    backdropPath,
    firstAirDate,
    genreIds,
    id,
    name,
    originCountry,
    originalLanguage,
    originalName,
    overview,
    popularity,
    posterPath,
    voteAverage,
    voteCount
)

fun TvSeriesDTO.toTvSeriesModel() = TvSeriesModel(
    page = page,
    totalPages = totalPages,
    totalResults = totalResults,
    tvSeriesResults = tvSeriesResults.toTvSeriesResultModel()
)

fun List<com.bashirli.mymovie.data.dto.details.tvseries.Genre>.toGenreModel()=map {
    with(it){
        GenreDetailsModel(
            id,name
        )
    }
}

fun List<com.bashirli.mymovie.data.dto.details.tvseries.ProductionCompany>
.toCompanyModel()=map {
    with(it){
        ProductionCompanyModel(
            id, logoPath, name, originCountry
        )
    }
}

fun List<com.bashirli.mymovie.data.dto.details.tvseries.SpokenLanguage>
.toSpokenModel()=map {
    with(it){
        SpokenLanguageModel(
            englishName, iso6391, name
        )
    }
}

fun List<Season>.toSeasonModel()=map {
    with(it){
        SeasonsModel(
            airDate, episodeCount, id, name, overview, posterPath, seasonNumber, voteAverage
        )
    }
}

fun TvSeriesDetailsDTO.toDetailsModel() = TvSeriesDetailsModel(
    adult,
    backdropPath,
    firstAirDate,
    genres.toGenreModel(),
    id,
    languages,
    name,
    numberOfEpisodes,
    numberOfSeasons,
    originCountry,
    originalLanguage,
    originalName,
    overview,
    popularity,
    posterPath,
    productionCompanies.toCompanyModel(),
    seasons.toSeasonModel(),
    spokenLanguages.toSpokenModel(),
    status,
    tagline,
    type,
    voteAverage,
    voteCount
)


fun List<FavoritesDTO>.toFavoritesModel()=map {
    with(it){
        FavoritesModel(
            id, title, voteAverage, releaseYear, image
        )
    }
}