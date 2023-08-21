package com.bashirli.mymovie.data.source

import android.graphics.pdf.PdfDocument.Page
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bashirli.mymovie.common.serviceEnum.ServiceEnumType
import com.bashirli.mymovie.common.serviceEnum.TvSeriesServiceEnumType
import com.bashirli.mymovie.common.util.Resource
import com.bashirli.mymovie.common.util.findExceptionMessage
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
import com.bashirli.mymovie.data.paging.CelebsPaging
import com.bashirli.mymovie.data.paging.MoviesPaging
import com.bashirli.mymovie.data.paging.TvSeriesPaging
import com.bashirli.mymovie.data.service.Service
import com.bashirli.mymovie.domain.model.details.reviews.ReviewModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class ApiSourceImpl @Inject constructor(
    private val service:Service
) : ApiSource {
    override suspend fun getMovies(): Flow<PagingData<Result>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            MoviesPaging(service,ServiceEnumType.GET_MOVIES)
        }
    ).flow

    override suspend fun getTrendingDayMovies(): Flow<PagingData<Result>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            MoviesPaging(service,ServiceEnumType.DAILY_TRENDING_MOVIES)
        }
    ).flow

    override suspend fun getUpcomingMovies(): Flow<PagingData<Result>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            MoviesPaging(service,ServiceEnumType.UPCOMING_MOVIES)
        }
    ).flow

    override suspend fun getForYouMovies(): Flow<PagingData<Result>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            MoviesPaging(service,ServiceEnumType.FOR_YOU)
        }
    ).flow


    override suspend fun getRecommendations(movieId: Int): Flow<PagingData<Result>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            enablePlaceholders = true,
            jumpThreshold = Int.MIN_VALUE
        ),
        pagingSourceFactory = {
            MoviesPaging(service,ServiceEnumType.RECOMMENDATIONS,movieId)
        }
    ).flow

    override suspend fun getMovieReviews(movieId: Int): Resource<ReviewDTO> {
         return try {
            val response=service.getMovieReviews(movieId)
            if(response.isSuccessful){
                response.body()?.let {
                    Resource.success(it)
                }?:Resource.error("Error",null)
            }else{
                Resource.error(findExceptionMessage(response.errorBody()),null)
            }
        }catch (e:Exception){
            Resource.error(e.localizedMessage,null)
        }
    }

    override suspend fun getCelebs(): Flow<PagingData<CelebsResult>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            enablePlaceholders = true,
            jumpThreshold = Int.MIN_VALUE
        ),
        pagingSourceFactory = {
            CelebsPaging(service)
        }
    ).flow

    override suspend fun getSearchMovies(query: String): Flow<PagingData<Result>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            enablePlaceholders = true,
            jumpThreshold = Int.MIN_VALUE
        ),
        pagingSourceFactory = {
            MoviesPaging(service,ServiceEnumType.SEARCH, query = query)
        }
    ).flow

    override suspend fun getDiscoverTvSeries(): Flow<PagingData<TvSeriesResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                enablePlaceholders = true,
                jumpThreshold = Int.MIN_VALUE
            ),
            pagingSourceFactory = {
                TvSeriesPaging(service,TvSeriesServiceEnumType.DISCOVER)
            }
        ).flow
    }

    override suspend fun getTvSeriesCategories(): Resource<GenreDTO> {
        return  handleResponse(service.getTvSeriesCategories())
    }

    override suspend fun getTvSeriesTrending(): Flow<PagingData<TvSeriesResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                enablePlaceholders = true,
                jumpThreshold = Int.MIN_VALUE
            ),
            pagingSourceFactory = {
                TvSeriesPaging(service,TvSeriesServiceEnumType.TRENDING)
            }
        ).flow
    }

    override suspend fun getTvSeriesAiringToday(): Flow<PagingData<TvSeriesResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                enablePlaceholders = true,
                jumpThreshold = Int.MIN_VALUE
            ),
            pagingSourceFactory = {
                TvSeriesPaging(service,TvSeriesServiceEnumType.AIRING_TODAY)
            }
        ).flow
    }

    override suspend fun getTvSeriesOnTheAir(): Flow<PagingData<TvSeriesResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                enablePlaceholders = true,
                jumpThreshold = Int.MIN_VALUE
            ),
            pagingSourceFactory = {
                TvSeriesPaging(service,TvSeriesServiceEnumType.ON_THE_AIR)
            }
        ).flow
    }

    override suspend fun getTvSeriesPopular(): Flow<PagingData<TvSeriesResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                enablePlaceholders = true,
                jumpThreshold = Int.MIN_VALUE
            ),
            pagingSourceFactory = {
                TvSeriesPaging(service,TvSeriesServiceEnumType.POPULAR)
            }
        ).flow
    }

    override suspend fun getTvSeriesTopRated(): Flow<PagingData<TvSeriesResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                enablePlaceholders = true,
                jumpThreshold = Int.MIN_VALUE
            ),
            pagingSourceFactory = {
                TvSeriesPaging(service,TvSeriesServiceEnumType.TOP_RATED)
            }
        ).flow
    }

    override suspend fun getTvSeriesImages(): Resource<TvSeriesDTO> {
        return  handleResponse(service.getTvSeriesImages())
    }

    override suspend fun getTvSeriesDetailsImages(seriesId: Int): Resource<ImagesDTO> {
        return handleResponse(service.getTvSeriesDetailsImages(seriesId))
    }

    override suspend fun getTvSeriesDetails(seriesId: Int): Resource<TvSeriesDetailsDTO> {
        return  handleResponse(service.getTvSeriesDetails(seriesId))
    }

    override suspend fun getTvSeriesRecommendations(seriesId: Int): Flow<PagingData<TvSeriesResult>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            enablePlaceholders = true,
            jumpThreshold = Int.MIN_VALUE
        ),
        pagingSourceFactory = {
            TvSeriesPaging(service,TvSeriesServiceEnumType.RECOMMENDATIONS,seriesId)
        }
    ).flow

    override suspend fun getTvSeriesReviews(seriesId: Int): Resource<ReviewDTO> {
        return handleResponse(service.getTvSeriesReviews(seriesId))
    }

    override suspend fun getTvSeriesCredit(seriesId: Int): Resource<CastDTO> {
        return handleResponse(service.getTvSeriesCredits(seriesId))
    }

    override suspend fun getCelebDetails(personId: Int): Resource<CelebDetailsDTO> {
        return  handleResponse(service.getCelebDetails(personId))
    }

    override suspend fun getCelebMovies(personId: Int): Resource<CelebMoviesDTO> {
        return  handleResponse(service.getCelebMovies(personId))
    }

    override suspend fun getCelebTvSeries(personId: Int): Resource<CelebTvSeriesDTO> {
        return  handleResponse(service.getCelebTvSeries(personId))
    }



    override suspend fun getMovieCategories(): Resource<GenreDTO> {
        return  handleResponse(service.getMovieCategories())
    }

    override suspend fun getSlideImages(): Resource<List<Result>> {
        return try {
            val response=service.getSlideImages()
            if(response.isSuccessful){
                response.body()?.let {
                    Resource.success(it.results)
                }?:Resource.error("Error",null)
            }else{
                Resource.error(findExceptionMessage(response.errorBody()),null)
            }
        }catch (e:Exception){
            Resource.error(e.localizedMessage,null)
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Resource<DetailsDTO> {
        return  handleResponse(service.getMovieDetails(movieId))
    }

    override suspend fun getDetailsCredit(movieId: Int): Resource<CastDTO> {
        return  handleResponse(service.getDetailsCredits(movieId))
    }

    override suspend fun getDetailsImages(movieId: Int): Resource<ImagesDTO> {
            return  handleResponse(service.getDetailsImages(movieId))
    }

   private fun <T> handleResponse(response:Response<T>) : Resource<T>{
       return  try {
            if(response.isSuccessful){
                response.body()?.let {
                    Resource.success(it)
                }?:Resource.error("Error",null)
            }else{
                Resource.error(findExceptionMessage(response.errorBody()),null)
            }
        }catch (e:Exception){
            Resource.error(e.localizedMessage,null)
        }
    }

}