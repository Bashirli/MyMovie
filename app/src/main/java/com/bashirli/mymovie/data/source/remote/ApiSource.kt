package com.bashirli.mymovie.data.source.remote

import androidx.paging.PagingData
import com.bashirli.mymovie.common.util.Resource
import com.bashirli.mymovie.data.dto.category.GenreDTO
import com.bashirli.mymovie.data.dto.celebs.CelebsResult
import com.bashirli.mymovie.data.dto.celebs.details.CelebDetailsDTO
import com.bashirli.mymovie.data.dto.celebs.movies.CelebMoviesDTO
import com.bashirli.mymovie.data.dto.celebs.tvseries.CelebTvSeriesDTO
import com.bashirli.mymovie.data.dto.details.movie.DetailsDTO
import com.bashirli.mymovie.data.dto.details.cast.CastDTO
import com.bashirli.mymovie.data.dto.details.images.ImagesDTO
import com.bashirli.mymovie.data.dto.details.reviews.ReviewDTO
import com.bashirli.mymovie.data.dto.details.tvseries.TvSeriesDetailsDTO
import com.bashirli.mymovie.data.dto.movies.Result
import com.bashirli.mymovie.data.dto.tvseries.TvSeriesDTO
import com.bashirli.mymovie.data.dto.tvseries.TvSeriesResult
import com.bashirli.mymovie.domain.model.details.reviews.ReviewModel
import kotlinx.coroutines.flow.Flow

interface ApiSource {

    suspend fun getMovies(): Flow<PagingData<Result>>

    suspend fun getTrendingDayMovies() : Flow<PagingData<Result>>

    suspend fun getUpcomingMovies() : Flow<PagingData<Result>>

    suspend fun getForYouMovies() : Flow<PagingData<Result>>

    suspend fun getMovieCategories() : Resource<GenreDTO>

    suspend fun getSlideImages() : Resource<List<Result>>

    suspend fun getMovieDetails(movieId:Int) : Resource<DetailsDTO>

    suspend fun getDetailsCredit(movieId: Int) : Resource<CastDTO>

    suspend fun getDetailsImages(movieId: Int) : Resource<ImagesDTO>

    suspend fun getRecommendations(movieId:Int) : Flow<PagingData<Result>>

    suspend fun getMovieReviews(movieId: Int) : Resource<ReviewDTO>

    suspend fun getCelebs():Flow<PagingData<CelebsResult>>

    suspend fun getCelebDetails(personId:Int):Resource<CelebDetailsDTO>

    suspend fun getCelebMovies(personId: Int) : Resource<CelebMoviesDTO>

    suspend fun getCelebTvSeries(personId: Int) : Resource<CelebTvSeriesDTO>

    suspend fun getSearchMovies(query:String) : Flow<PagingData<Result>>


    suspend fun getDiscoverTvSeries() : Flow<PagingData<TvSeriesResult>>

    suspend fun getTvSeriesCategories() : Resource<GenreDTO>

    suspend fun getTvSeriesTrending() : Flow<PagingData<TvSeriesResult>>

    suspend fun getTvSeriesAiringToday() : Flow<PagingData<TvSeriesResult>>

    suspend fun getTvSeriesOnTheAir() : Flow<PagingData<TvSeriesResult>>

    suspend fun getTvSeriesPopular() : Flow<PagingData<TvSeriesResult>>

    suspend fun getTvSeriesTopRated() : Flow<PagingData<TvSeriesResult>>

    suspend fun getTvSeriesImages() : Resource<TvSeriesDTO>

    suspend fun getTvSeriesDetails(seriesId:Int) : Resource<TvSeriesDetailsDTO>

    suspend fun getTvSeriesRecommendations(seriesId:Int) : Flow<PagingData<TvSeriesResult>>

    suspend fun getTvSeriesReviews(seriesId:Int) : Resource<ReviewDTO>

    suspend fun getTvSeriesDetailsImages(seriesId:Int) : Resource<ImagesDTO>

    suspend fun getTvSeriesCredit(seriesId:Int) : Resource<CastDTO>

}