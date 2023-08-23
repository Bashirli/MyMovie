package com.bashirli.mymovie.domain.repository.remote

import androidx.paging.PagingData
import com.bashirli.mymovie.common.util.Resource
import com.bashirli.mymovie.data.dto.details.cast.CastDTO
import com.bashirli.mymovie.data.dto.details.images.ImagesDTO
import com.bashirli.mymovie.data.dto.details.reviews.ReviewDTO
import com.bashirli.mymovie.data.dto.tvseries.TvSeriesResult
import com.bashirli.mymovie.domain.model.celebs.CelebsResultModel
import com.bashirli.mymovie.domain.model.celebs.detail.CelebDetailsModel
import com.bashirli.mymovie.domain.model.celebs.movies.CelebMoviesModel
import com.bashirli.mymovie.domain.model.celebs.tvSeries.CelebTvSeriesModel
import com.bashirli.mymovie.domain.model.details.movie.DetailsModel
import com.bashirli.mymovie.domain.model.details.cast.CastBaseModel
import com.bashirli.mymovie.domain.model.details.cast.CastModel
import com.bashirli.mymovie.domain.model.details.images.ImagesModel
import com.bashirli.mymovie.domain.model.details.reviews.ReviewModel
import com.bashirli.mymovie.domain.model.details.tvseries.TvSeriesDetailsModel
import com.bashirli.mymovie.domain.model.movies.GenreBaseModel
import com.bashirli.mymovie.domain.model.movies.ResultModel
import com.bashirli.mymovie.domain.model.tvseries.TvSeriesModel
import com.bashirli.mymovie.domain.model.tvseries.TvSeriesResultModel
import kotlinx.coroutines.flow.Flow

interface ApiRepository {

    suspend fun getMovies() : Flow<PagingData<ResultModel>>

    suspend fun getTrendingDayMovies() : Flow<PagingData<ResultModel>>

    suspend fun getUpcomingMovies() : Flow<PagingData<ResultModel>>

    suspend fun getForYouMovies() : Flow<PagingData<ResultModel>>

    suspend fun getMovieCategories() : Flow<Resource<GenreBaseModel>>

    suspend fun getSlideImages() : Flow<Resource<List<ResultModel>>>

    suspend fun getMovieDetails(movieId:Int) : Flow<Resource<DetailsModel>>

    suspend fun getDetailsCredit(movieId:Int) : Flow<Resource<CastBaseModel>>

    suspend fun getDetailsImages(movieId:Int) : Flow<Resource<ImagesModel>>

    suspend fun getRecommendations(movieId:Int) : Flow<PagingData<ResultModel>>

    suspend fun getMovieReviews(movieId:Int) : Flow<Resource<ReviewModel>>

    suspend fun getCelebs():Flow<PagingData<CelebsResultModel>>

    suspend fun getCelebDetails(personId:Int) : Flow<Resource<CelebDetailsModel>>

    suspend fun getCelebMovies(personId:Int) : Flow<Resource<CelebMoviesModel>>

    suspend fun getCelebTvSeries(personId: Int) : Flow<Resource<CelebTvSeriesModel>>

    suspend fun getSearchMovies(query:String) : Flow<PagingData<ResultModel>>

    suspend fun getDiscoverTvSeries() : Flow<PagingData<TvSeriesResultModel>>

    suspend fun getTvSeriesCategories() : Flow<Resource<GenreBaseModel>>

    suspend fun getTvSeriesTrending() : Flow<PagingData<TvSeriesResultModel>>

    suspend fun getTvSeriesAiringToday() : Flow<PagingData<TvSeriesResultModel>>

    suspend fun getTvSeriesOnTheAir() : Flow<PagingData<TvSeriesResultModel>>

    suspend fun getTvSeriesPopular() : Flow<PagingData<TvSeriesResultModel>>

    suspend fun getTvSeriesTopRated() : Flow<PagingData<TvSeriesResultModel>>

    suspend fun getTvSeriesImages() : Flow<Resource<TvSeriesModel>>

    suspend fun getTvSeriesDetails(seriesId:Int) : Flow<Resource<TvSeriesDetailsModel>>

    suspend fun getTvSeriesRecommendations(seriesId:Int) : Flow<PagingData<TvSeriesResultModel>>

    suspend fun getTvSeriesReviews(seriesId:Int) : Flow<Resource<ReviewModel>>

    suspend fun getTvSeriesDetailsImages(seriesId:Int) : Flow<Resource<ImagesModel>>

    suspend fun getTvSeriesCredit(seriesId:Int) : Flow<Resource<CastBaseModel>>


}