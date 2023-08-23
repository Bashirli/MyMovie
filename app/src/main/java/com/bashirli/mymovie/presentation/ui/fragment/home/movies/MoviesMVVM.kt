package com.bashirli.mymovie.presentation.ui.fragment.home.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bashirli.mymovie.common.util.Resource
import com.bashirli.mymovie.domain.model.movies.GenreBaseModel
import com.bashirli.mymovie.domain.model.movies.ResultModel
import com.bashirli.mymovie.domain.useCase.remote.movies.GetForYouMoviesUseCase
import com.bashirli.mymovie.domain.useCase.remote.details.GetMovieCategoriesUseCase
import com.bashirli.mymovie.domain.useCase.remote.movies.GetMoviesUseCase
import com.bashirli.mymovie.domain.useCase.remote.movies.GetSlideImagesUseCase
import com.bashirli.mymovie.domain.useCase.remote.movies.GetTrendingDayMoviesUseCase
import com.bashirli.mymovie.domain.useCase.remote.movies.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesMVVM @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getTrendingDayMoviesUseCase: GetTrendingDayMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getForYouMoviesUseCase: GetForYouMoviesUseCase,
    private val getMovieCategoriesUseCase: GetMovieCategoriesUseCase,
    private val getSlideImagesUseCase: GetSlideImagesUseCase
) : ViewModel() {

    private val _pagingMovie=MutableLiveData<PagingData<ResultModel>>(PagingData.empty())
    val pagingMovie:LiveData<PagingData<ResultModel>> = _pagingMovie

    private val _pagingTrendingMovies=MutableLiveData<PagingData<ResultModel>>(PagingData.empty())
    val pagingTrendingMovies:LiveData<PagingData<ResultModel>> = _pagingTrendingMovies

    private val _pagingUpcomingMovies=MutableLiveData<PagingData<ResultModel>>(PagingData.empty())
    val pagingUpcomingMovies:LiveData<PagingData<ResultModel>> = _pagingUpcomingMovies

    private val _pagingForYouMovies=MutableLiveData<PagingData<ResultModel>>(PagingData.empty())
    val pagingForYouMovies:LiveData<PagingData<ResultModel>> = _pagingForYouMovies

    private val _categoriesModel=MutableLiveData<Resource<GenreBaseModel>>()
    val categoriesModel:LiveData<Resource<GenreBaseModel>> = _categoriesModel

    private val _slideImages=MutableLiveData<Resource<List<ResultModel>>>()
    val slideImages:LiveData<Resource<List<ResultModel>>> = _slideImages

    init{
        getMovies()
        getTrendingMovies()
        getUpcomingMovies()
        getForYouMovies()
        getSlideImages()
        getMovieCategories()
    }

    private fun getMovies(){
        viewModelScope.launch {
            getMoviesUseCase().cachedIn(viewModelScope).collectLatest {
                _pagingMovie.postValue(it)
            }
        }
    }

    private fun getTrendingMovies(){
        viewModelScope.launch {
            getTrendingDayMoviesUseCase().cachedIn(viewModelScope).collectLatest {
                _pagingTrendingMovies.postValue(it)
            }
        }
    }

    private fun getUpcomingMovies(){
        viewModelScope.launch {
            getUpcomingMoviesUseCase().cachedIn(viewModelScope).collectLatest {
                _pagingUpcomingMovies.postValue(it)
            }
        }
    }

    private fun getForYouMovies(){
        viewModelScope.launch {
            getForYouMoviesUseCase().cachedIn(viewModelScope).collectLatest {
                _pagingForYouMovies.postValue(it)
            }
        }
    }

    private fun getMovieCategories(){
        viewModelScope.launch {
            getMovieCategoriesUseCase().collectLatest {
                _categoriesModel.value=it
            }
        }
    }

    private fun getSlideImages(){
        viewModelScope.launch {
            getSlideImagesUseCase().collectLatest {
                _slideImages.value=it
            }
        }
    }

}