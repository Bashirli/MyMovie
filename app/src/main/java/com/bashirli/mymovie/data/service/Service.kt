package com.bashirli.mymovie.data.service

import com.bashirli.mymovie.data.dto.category.GenreDTO
import com.bashirli.mymovie.data.dto.celebs.CelebsDTO
import com.bashirli.mymovie.data.dto.celebs.details.CelebDetailsDTO
import com.bashirli.mymovie.data.dto.celebs.movies.CelebMoviesDTO
import com.bashirli.mymovie.data.dto.celebs.tvseries.CelebTvSeriesDTO
import com.bashirli.mymovie.data.dto.details.movie.DetailsDTO
import com.bashirli.mymovie.data.dto.details.cast.CastDTO
import com.bashirli.mymovie.data.dto.details.images.ImagesDTO
import com.bashirli.mymovie.data.dto.details.reviews.ReviewDTO
import com.bashirli.mymovie.data.dto.details.tvseries.TvSeriesDetailsDTO
import com.bashirli.mymovie.data.dto.movies.MoviesDTO
import com.bashirli.mymovie.data.dto.tvseries.TvSeriesDTO
import com.bashirli.mymovie.domain.model.celebs.tvSeries.CelebTvCastModel
import com.bashirli.mymovie.domain.model.details.reviews.ReviewModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {

    @GET("discover/movie")
    suspend fun getMovies(@Query("page") page:Int):Response<MoviesDTO>

    @GET("trending/movie/day")
    suspend fun getTrendingDayMovies(@Query("page") page:Int) : Response<MoviesDTO>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page : Int) : Response<MoviesDTO>

    @GET("movie/top_rated")
    suspend fun getForYouMovies(@Query("page") page : Int) : Response<MoviesDTO>

    @GET("genre/movie/list")
    suspend fun getMovieCategories():Response<GenreDTO>

    @GET("movie/popular")
    suspend fun getSlideImages():Response<MoviesDTO>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId:Int):Response<DetailsDTO>

    @GET("movie/{movie_id}/credits")
    suspend fun getDetailsCredits(@Path("movie_id") movieId:Int) : Response<CastDTO>

    @GET("movie/{movie_id}/images")
    suspend fun getDetailsImages(@Path("movie_id") movieId:Int) : Response<ImagesDTO>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendations(@Path("movie_id") movieId:Int, @Query("page") page : Int) : Response<MoviesDTO>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(@Path("movie_id") movieId:Int) : Response<ReviewDTO>

    @GET("person/popular")
    suspend fun getCelebs(@Query("page") page:Int) : Response<CelebsDTO>

    @GET("person/{person_id}")
    suspend fun getCelebDetails(@Path("person_id") personId:Int) : Response<CelebDetailsDTO>

    @GET("person/{person_id}/movie_credits")
    suspend fun getCelebMovies(@Path("person_id") personId:Int) : Response<CelebMoviesDTO>

    @GET("person/{person_id}/tv_credits")
    suspend fun getCelebTvSeries(@Path("person_id") personId:Int) : Response<CelebTvSeriesDTO>


    @GET("search/movie")
    suspend fun getSearchMovie(
        @Query("query") query:String,
        @Query("page") page:Int
        ) : Response<MoviesDTO>

    @GET("discover/tv")
    suspend fun getDiscoverTvSeries(@Query("page") page:Int ) : Response<TvSeriesDTO>

    @GET("genre/tv/list")
    suspend fun getTvSeriesCategories() : Response<GenreDTO>

    @GET("trending/tv/{time_window}")
    suspend fun getTvSeriesTrending(
        @Path("time_window") timeWindow:String = "day",
        @Query("page") page:Int
    ) : Response<TvSeriesDTO>

    @GET("tv/airing_today")
    suspend fun getTvSeriesAiringToday(@Query("page") page:Int) : Response<TvSeriesDTO>

    @GET("tv/on_the_air")
    suspend fun getTvSeriesOnTheAir(@Query("page") page:Int) : Response<TvSeriesDTO>

    @GET("tv/popular")
    suspend fun getTvSeriesPopular(@Query("page") page:Int) : Response<TvSeriesDTO>

    @GET("tv/top_rated")
    suspend fun getTvSeriesTopRated(@Query("page") page:Int) : Response<TvSeriesDTO>

    @GET("tv/top_rated")
    suspend fun getTvSeriesImages() : Response<TvSeriesDTO>

    @GET("tv/{series_id}")
    suspend fun getTvSeriesDetails(@Path("series_id") seriesId:Int) : Response<TvSeriesDetailsDTO>

    @GET("tv/{series_id}/recommendations")
    suspend fun getTvSeriesRecommendations(@Path("series_id") seriesId:Int) : Response<TvSeriesDTO>

    @GET("tv/{series_id}/reviews")
    suspend fun getTvSeriesReviews(@Path("series_id") seriesId:Int) : Response<ReviewDTO>

    @GET("tv/{series_id}/images")
    suspend fun getTvSeriesDetailsImages(@Path("series_id") seriesId:Int) : Response<ImagesDTO>

    @GET("tv/{series_id}/credits")
    suspend fun getTvSeriesCredits(@Path ("series_id") seriesId:Int) : Response<CastDTO>


}