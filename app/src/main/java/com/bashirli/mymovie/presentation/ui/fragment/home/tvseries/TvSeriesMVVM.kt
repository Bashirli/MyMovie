package com.bashirli.mymovie.presentation.ui.fragment.home.tvseries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bashirli.mymovie.common.util.Resource
import com.bashirli.mymovie.common.util.Status
import com.bashirli.mymovie.data.dto.tvseries.TvSeriesDTO
import com.bashirli.mymovie.domain.model.movies.GenreBaseModel
import com.bashirli.mymovie.domain.model.tvseries.TvSeriesModel
import com.bashirli.mymovie.domain.model.tvseries.TvSeriesResultModel
import com.bashirli.mymovie.domain.useCase.tvseries.GetAiringTodayTvSeriesUseCase
import com.bashirli.mymovie.domain.useCase.tvseries.GetCategoriesTvSeriesUseCase
import com.bashirli.mymovie.domain.useCase.tvseries.GetDiscoverTvSeriesUseCase
import com.bashirli.mymovie.domain.useCase.tvseries.GetOnTheAirUseTvSeriesCase
import com.bashirli.mymovie.domain.useCase.tvseries.GetPopularTvSeriesUseCase
import com.bashirli.mymovie.domain.useCase.tvseries.GetTopRatedTvSeriesUseCase
import com.bashirli.mymovie.domain.useCase.tvseries.GetTrendingTvSeriesUseCase
import com.bashirli.mymovie.domain.useCase.tvseries.GetTvSeriesImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvSeriesMVVM @Inject constructor(
    private val getAiringTodayTvSeriesUseCase: GetAiringTodayTvSeriesUseCase,
    private val getCategoriesTvSeriesUseCase: GetCategoriesTvSeriesUseCase,
    private val getDiscoverTvSeriesUseCase: GetDiscoverTvSeriesUseCase,
    private val getOnTheAirUseTvSeriesCase: GetOnTheAirUseTvSeriesCase,
    private val getPopularTvSeriesUseCase: GetPopularTvSeriesUseCase,
    private val getTvSeriesImagesUseCase: GetTvSeriesImagesUseCase,
    private val getTopRatedTvSeriesUseCase: GetTopRatedTvSeriesUseCase,
    private val getTrendingTvSeriesUseCase: GetTrendingTvSeriesUseCase
) : ViewModel() {

    private val _liveData = MutableLiveData<State> ()
    val liveData : LiveData<State> get()=_liveData

    private val _airingTodayData = MutableLiveData<PagingData<TvSeriesResultModel>>()
    val airingTodayData : LiveData<PagingData<TvSeriesResultModel>> get()= _airingTodayData

    private val _onTheAirData = MutableLiveData<PagingData<TvSeriesResultModel>>()
    val onTheAirData : LiveData<PagingData<TvSeriesResultModel>> get()= _onTheAirData

    private val _popularData = MutableLiveData<PagingData<TvSeriesResultModel>>()
    val popularData : LiveData<PagingData<TvSeriesResultModel>> get()= _popularData

    private val _topRatedData = MutableLiveData<PagingData<TvSeriesResultModel>>()
    val topRatedData : LiveData<PagingData<TvSeriesResultModel>> get()= _topRatedData

    private val _discoverData = MutableLiveData<PagingData<TvSeriesResultModel>>()
    val discoverData : LiveData<PagingData<TvSeriesResultModel>> get()= _discoverData

    private val _trendingData = MutableLiveData<PagingData<TvSeriesResultModel>>()
    val trendingData : LiveData<PagingData<TvSeriesResultModel>> get()= _trendingData


    init {
        call()
    }

    private fun call(){
        _liveData.value=State.Loading
        getAiringToday()
        getOnTheAir()
        getPopular()
        getTopRated()
        getDiscover()
        getCategories()
        getImages()
        getTrending()
    }

    private fun getAiringToday(){
        viewModelScope.launch {
            getAiringTodayTvSeriesUseCase().cachedIn(viewModelScope).collectLatest {
                _airingTodayData.postValue(it)
            }
        }
    }

    private fun getCategories(){
        viewModelScope.launch {
            getCategoriesTvSeriesUseCase().collectLatest {
                when(it.status){
                    Status.LOADING->{

                    }
                    Status.SUCCESS->{
                       it.data?.let {
                           _liveData.value=State.Categories(it)
                       }
                    }
                    Status.ERROR->{
                        _liveData.value=State.Error(it.message?:"Error")
                    }
                }
            }
        }
    }


    private fun getOnTheAir(){
        viewModelScope.launch {
            getOnTheAirUseTvSeriesCase().cachedIn(viewModelScope).collectLatest {
                _onTheAirData.postValue(it)
            }
        }
    }


    private fun getTrending(){
        viewModelScope.launch {
            getTrendingTvSeriesUseCase().cachedIn(viewModelScope).collectLatest {
                _trendingData.postValue(it)
            }
        }
    }


    private fun getDiscover(){
        viewModelScope.launch {
            getDiscoverTvSeriesUseCase().cachedIn(viewModelScope).collectLatest {
                _discoverData.postValue(it)
            }
        }
    }



    private fun getPopular(){
        viewModelScope.launch {
            getPopularTvSeriesUseCase().cachedIn(viewModelScope).collectLatest {
                _popularData.postValue(it)
            }
        }
    }



    private fun getTopRated(){
        viewModelScope.launch {
            getTopRatedTvSeriesUseCase().cachedIn(viewModelScope).collectLatest {
                _topRatedData.postValue(it)
            }
        }
    }

    private fun getImages(){
        viewModelScope.launch {
            getTvSeriesImagesUseCase().collectLatest {
                when(it.status){
                    Status.SUCCESS->{
                        it.data?.let {
                            _liveData.value=State.Images(it)
                        }
                    }
                    Status.ERROR->{
                        _liveData.value=State.Error(it.message?:"Error")
                    }
                    Status.LOADING->{}
                }
            }
        }
    }

    sealed class State(){

        data class Images(val data : TvSeriesModel) : State()

        data class Categories(val data : GenreBaseModel) : State()

        object Loading:State()

        data class Error(val message:String) : State()

    }

}