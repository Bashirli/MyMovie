package com.bashirli.mymovie.data.repository.remote

import androidx.paging.PagingData
import androidx.paging.map
import com.bashirli.mymovie.common.util.Resource
import com.bashirli.mymovie.common.util.Status
import com.bashirli.mymovie.data.mapper.toCastBaseModel
import com.bashirli.mymovie.data.mapper.toCategoryModel
import com.bashirli.mymovie.data.mapper.toCelebDetailsModel
import com.bashirli.mymovie.data.mapper.toCelebMoviesModel
import com.bashirli.mymovie.data.mapper.toCelebTvSeriesModel
import com.bashirli.mymovie.data.mapper.toCelebsResultModel
import com.bashirli.mymovie.data.mapper.toDetailsModel
import com.bashirli.mymovie.data.mapper.toGenreBaseModel
import com.bashirli.mymovie.data.mapper.toImagesModel
import com.bashirli.mymovie.data.mapper.toResultModel
import com.bashirli.mymovie.data.mapper.toReviewModel
import com.bashirli.mymovie.data.mapper.toTvSeriesModel
import com.bashirli.mymovie.data.mapper.toTvSeriesResultModel
import com.bashirli.mymovie.data.source.remote.ApiSource
import com.bashirli.mymovie.domain.model.categorySelected.CategoryDetailsModel
import com.bashirli.mymovie.domain.model.celebs.CelebsResultModel
import com.bashirli.mymovie.domain.model.celebs.detail.CelebDetailsModel
import com.bashirli.mymovie.domain.model.celebs.movies.CelebMoviesModel
import com.bashirli.mymovie.domain.model.celebs.tvSeries.CelebTvSeriesModel
import com.bashirli.mymovie.domain.model.details.cast.CastBaseModel
import com.bashirli.mymovie.domain.model.details.images.ImagesModel
import com.bashirli.mymovie.domain.model.details.movie.DetailsModel
import com.bashirli.mymovie.domain.model.details.reviews.ReviewModel
import com.bashirli.mymovie.domain.model.details.tvseries.TvSeriesDetailsModel
import com.bashirli.mymovie.domain.model.movies.GenreBaseModel
import com.bashirli.mymovie.domain.model.movies.ResultModel
import com.bashirli.mymovie.domain.model.tvseries.TvSeriesModel
import com.bashirli.mymovie.domain.model.tvseries.TvSeriesResultModel
import com.bashirli.mymovie.domain.repository.remote.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ApiRepositoryImpl constructor(
    private val apiSource: ApiSource,
) : ApiRepository {
    override suspend fun getMovies(): Flow<PagingData<ResultModel>> = flow {
        val response = apiSource.getMovies()
        response.map { pagingData ->
            pagingData.map {
                it.toResultModel()
            }

        }.collect {
            emit(it)
        }
    }

    override suspend fun getTrendingDayMovies(): Flow<PagingData<ResultModel>> = flow {
        val response = apiSource.getTrendingDayMovies()
        response.map { data ->
            data.map {
                it.toResultModel()
            }
        }.collect {
            emit(it)
        }
    }

    override suspend fun getUpcomingMovies(): Flow<PagingData<ResultModel>> = flow {
        val response = apiSource.getUpcomingMovies()
        response.map { data ->
            data.map {
                it.toResultModel()
            }
        }.collect {
            emit(it)
        }
    }

    override suspend fun getForYouMovies(): Flow<PagingData<ResultModel>> = flow {
        val response = apiSource.getForYouMovies()
        response.map { pagingData ->
            pagingData.map {
                it.toResultModel()
            }
        }.collect {
            emit(it)
        }
    }

    override suspend fun getRecommendations(personId: Int): Flow<PagingData<ResultModel>> = flow {
        val response = apiSource.getRecommendations(personId)
        response.map { pagingData ->
            pagingData.map {
                it.toResultModel()
            }
        }.collect {
            emit(it)
        }
    }

    override suspend fun getMovieReviews(personId: Int): Flow<Resource<ReviewModel>> = flow {
        emit(Resource.loading(null))
        val response = apiSource.getMovieReviews(personId)
        when (response.status) {
            Status.ERROR -> {
                emit(Resource.error(response.message ?: "Error", null))
            }

            Status.SUCCESS -> {
                emit(Resource.success(response.data?.toReviewModel()))
            }

            else -> {}
        }
    }

    override suspend fun getCelebs(): Flow<PagingData<CelebsResultModel>> = flow {
        val response = apiSource.getCelebs()
        response.map {
            it.map {
                it.toCelebsResultModel()
            }
        }.collect {
            emit(it)
        }
    }

    override suspend fun getCelebDetails(personId: Int): Flow<Resource<CelebDetailsModel>> = flow {
        emit(Resource.loading(null))
        val response = apiSource.getCelebDetails(personId)
        when (response.status) {
            Status.ERROR -> {
                emit(Resource.error(response.message ?: "Error", null))
            }

            Status.SUCCESS -> {
                emit(Resource.success(response.data?.toCelebDetailsModel()))
            }

            else -> {}
        }
    }

    override suspend fun getCelebMovies(personId: Int): Flow<Resource<CelebMoviesModel>> = flow {
        emit(Resource.loading(null))
        val response = apiSource.getCelebMovies(personId)
        when (response.status) {
            Status.SUCCESS -> {
                emit(Resource.success(response.data?.toCelebMoviesModel()))
            }

            Status.ERROR -> {
                emit(Resource.error(response.message ?: "Error", null))
            }

            else -> {}
        }

    }

    override suspend fun getCelebTvSeries(personId: Int): Flow<Resource<CelebTvSeriesModel>> =
        flow {
            emit(Resource.loading(null))
            val response = apiSource.getCelebTvSeries(personId)
            when (response.status) {
                Status.ERROR -> {
                    emit(Resource.error(response.message ?: "Error", null))
                }

                Status.SUCCESS -> {
                    emit(Resource.success(response.data?.toCelebTvSeriesModel()))
                }

                else -> {}
            }
        }

    override suspend fun getSearchMovies(query: String): Flow<PagingData<ResultModel>> = flow {
        val response = apiSource.getSearchMovies(query)
        response.map {
            it.map {
                it.toResultModel()
            }
        }.collect {
            emit(it)
        }


    }

    override suspend fun getDiscoverTvSeries(): Flow<PagingData<TvSeriesResultModel>> = flow {
        val response = apiSource.getDiscoverTvSeries()
        response.map { pagingData ->
            pagingData.map {
                it.toTvSeriesResultModel()
            }
        }.collect{
            emit(it)
        }
    }

    override suspend fun getTvSeriesCategories(): Flow<Resource<GenreBaseModel>> = flow {
        emit(Resource.loading(null))
        val response = apiSource.getTvSeriesCategories()
        when(response.status){
            Status.ERROR->{
                emit(Resource.error(response.message?:"Error",null))
            }
            Status.SUCCESS->{
                emit(Resource.success(response.data?.toGenreBaseModel()))
            }
            else->{}
        }


    }

    override suspend fun getTvSeriesTrending(): Flow<PagingData<TvSeriesResultModel>> = flow {
        val response = apiSource.getTvSeriesTrending()
        response.map { pagingData ->
            pagingData.map {
                it.toTvSeriesResultModel()
            }
        }.collect{
            emit(it)
        }
    }

    override suspend fun getTvSeriesAiringToday(): Flow<PagingData<TvSeriesResultModel>> =  flow {
        val response = apiSource.getTvSeriesAiringToday()
        response.map { pagingData ->
            pagingData.map {
                it.toTvSeriesResultModel()
            }
        }.collect{
            emit(it)
        }
    }

    override suspend fun getTvSeriesOnTheAir(): Flow<PagingData<TvSeriesResultModel>> = flow {
        val response = apiSource.getTvSeriesOnTheAir()
        response.map { pagingData ->
            pagingData.map {
                it.toTvSeriesResultModel()
            }
        }.collect{
            emit(it)
        }
    }


    override suspend fun getTvSeriesPopular(): Flow<PagingData<TvSeriesResultModel>> =  flow {
        val response = apiSource.getTvSeriesPopular()
        response.map { pagingData ->
            pagingData.map {
                it.toTvSeriesResultModel()
            }
        }.collect{
            emit(it)
        }
    }

    override suspend fun getTvSeriesTopRated(): Flow<PagingData<TvSeriesResultModel>> =  flow {
        val response = apiSource.getTvSeriesTopRated()
        response.map { pagingData ->
            pagingData.map {
                it.toTvSeriesResultModel()
            }
        }.collect{
            emit(it)
        }
    }

    override suspend fun getTvSeriesImages(): Flow<Resource<TvSeriesModel>> = flow {
        emit(Resource.loading(null))

        val response = apiSource.getTvSeriesImages()

        when(response.status){
            Status.ERROR->{
                emit(Resource.error(response.message?:"Error",null))
            }
            Status.SUCCESS->{
                emit(Resource.success(response.data?.toTvSeriesModel()))
            }
            else->{}
        }


    }

    override suspend fun getTvSeriesDetails(seriesId: Int): Flow<Resource<TvSeriesDetailsModel>> = flow {
        emit(Resource.loading(null))

        val response = apiSource.getTvSeriesDetails(seriesId)

        when(response.status){
             Status.ERROR->{
                 emit(Resource.error(response.message?:"Error",null))
             }
             Status.SUCCESS->{
                emit(Resource.success(response.data?.toDetailsModel()))
            }
            else->{}
        }

    }

    override suspend fun getTvSeriesRecommendations(seriesId: Int): Flow<PagingData<TvSeriesResultModel>> = flow {
        val response = apiSource.getTvSeriesRecommendations(seriesId)
        response.map { pagingData->
            pagingData.map {
                it.toTvSeriesResultModel()
            }
        }.collect{
            emit(it)
        }
    }

    override suspend fun getTvSeriesReviews(seriesId: Int): Flow<Resource<ReviewModel>> = flow {
        emit(Resource.loading(null))
        val response = apiSource.getTvSeriesReviews(seriesId)
        when (response.status) {
            Status.ERROR -> {
                emit(Resource.error(response.message ?: "Error", null))
            }

            Status.SUCCESS -> {
                emit(Resource.success(response.data?.toReviewModel()))
            }

            else -> {}
        }
    }

    override suspend fun getTvSeriesDetailsImages(seriesId: Int): Flow<Resource<ImagesModel>> = flow {
        emit(Resource.loading(null))
        val response = apiSource.getTvSeriesDetailsImages(seriesId)
        when (response.status) {
            Status.ERROR -> {
                emit(Resource.error(response.message ?: "Error", null))
            }

            Status.SUCCESS -> {
                emit(Resource.success(response.data?.toImagesModel()))
            }

            else -> {}
        }
    }

    override suspend fun getTvSeriesCredit(seriesId: Int): Flow<Resource<CastBaseModel>> = flow {
        emit(Resource.loading(null))
        val response = apiSource.getTvSeriesCredit(seriesId)
        when (response.status) {
            Status.ERROR -> {
                emit(Resource.error(response.message ?: "Error", null))
            }

            Status.SUCCESS -> {
                emit(Resource.success(response.data?.toCastBaseModel()))
            }

            else -> {}
        }
    }

    override suspend fun getCategoryDetails(listId: Int): Flow<Resource<CategoryDetailsModel>> = flow {
        emit(Resource.loading(null))
        val response = apiSource.getCategoryDetails(listId)
        when (response.status) {
            Status.ERROR -> {
                emit(Resource.error(response.message ?: "Error", null))
            }

            Status.SUCCESS -> {
                emit(Resource.success(response.data?.toCategoryModel()))
            }

            else -> {}
        }
    }


    override suspend fun getMovieCategories(): Flow<Resource<GenreBaseModel>> = flow {
        emit(Resource.loading(null))
        val response = apiSource.getMovieCategories()
        when (response.status) {
            Status.ERROR -> {
                emit(Resource.error(response.message ?: "Error", null))
            }

            Status.SUCCESS -> {
                emit(Resource.success(response.data?.toGenreBaseModel()))
            }

            else -> {}
        }
    }

    override suspend fun getSlideImages(): Flow<Resource<List<ResultModel>>> = flow {
        emit(Resource.loading(null))
        val response = apiSource.getSlideImages()
        when (response.status) {
            Status.ERROR -> {
                emit(Resource.error(response.message ?: "Error", null))
            }

            Status.SUCCESS -> {
                emit(Resource.success(response.data?.toResultModel()))
            }

            else -> {}
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<Resource<DetailsModel>> = flow {
        emit(Resource.loading(null))
        val response = apiSource.getMovieDetails(movieId)

        when (response.status) {
            Status.ERROR -> {
                emit(Resource.error(response.message ?: "Error", null))
            }

            Status.SUCCESS -> {
                emit(Resource.success(response.data?.toDetailsModel()))
            }

            else -> {}
        }

    }

    override suspend fun getDetailsCredit(movieId: Int): Flow<Resource<CastBaseModel>> = flow {
        emit(Resource.loading(null))
        val response = apiSource.getDetailsCredit(movieId)
        when (response.status) {
            Status.SUCCESS -> {
                emit(Resource.success(response.data?.toCastBaseModel()))
            }

            Status.ERROR -> {
                emit(Resource.error(response.message ?: "Error", null))
            }

            else -> {}
        }
    }

    override suspend fun getDetailsImages(movieId: Int): Flow<Resource<ImagesModel>> = flow {
        emit(Resource.loading(null))
        val response = apiSource.getDetailsImages(movieId)
        when (response.status) {
            Status.SUCCESS -> {
                emit(Resource.success(response.data?.toImagesModel()))
            }

            Status.ERROR -> {
                emit(Resource.error(response.message ?: "Error", null))
            }

            else -> {}
        }
    }


}