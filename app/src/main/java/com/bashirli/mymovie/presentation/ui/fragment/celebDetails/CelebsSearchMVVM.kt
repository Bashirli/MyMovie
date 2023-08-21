package com.bashirli.mymovie.presentation.ui.fragment.celebDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bashirli.mymovie.common.util.Resource
import com.bashirli.mymovie.domain.model.celebs.CelebsResultModel
import com.bashirli.mymovie.domain.model.celebs.detail.CelebDetailsModel
import com.bashirli.mymovie.domain.model.celebs.movies.CelebMoviesModel
import com.bashirli.mymovie.domain.model.celebs.tvSeries.CelebTvSeriesModel
import com.bashirli.mymovie.domain.useCase.celeb.GetCelebDetailsUseCase
import com.bashirli.mymovie.domain.useCase.celeb.GetCelebMoviesUseCase
import com.bashirli.mymovie.domain.useCase.celeb.GetCelebTvSeriesUseCase
import com.bashirli.mymovie.domain.useCase.celeb.GetCelebsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CelebsSearchMVVM @Inject constructor(
    private val getCelebDetailsUseCase: GetCelebDetailsUseCase,
    private val getCelebMoviesUseCase: GetCelebMoviesUseCase,
    private val getCelebsUseCase: GetCelebsUseCase,
    private val getCelebTvSeriesUseCase: GetCelebTvSeriesUseCase
) : ViewModel() {

    private val _celebData=MutableLiveData<Resource<CelebDetailsModel>>()
    val celebData:LiveData<Resource<CelebDetailsModel>> get()=_celebData

    private val _celebMoviesData=MutableLiveData<Resource<CelebMoviesModel>>()
    val celebMoviesData:LiveData<Resource<CelebMoviesModel>> get()=_celebMoviesData

    private val _celebTvSeriesData=MutableLiveData<Resource<CelebTvSeriesModel>>()
    val celebTvSeriesData:LiveData<Resource<CelebTvSeriesModel>> get()=_celebTvSeriesData

    private val _celebRecommendationData=MutableLiveData<PagingData<CelebsResultModel>>(PagingData.empty())
    val celebRecommendationData : LiveData<PagingData<CelebsResultModel>> get() = _celebRecommendationData


    init {
        getCelebs()
    }

    fun getCelebDetails(personId:Int){
        viewModelScope.launch {
            getCelebDetailsUseCase(personId).collectLatest {
                _celebData.value=it
            }
        }
    }

    fun getCelebMovies(personId:Int){
        viewModelScope.launch {
            getCelebMoviesUseCase(personId).collectLatest {
                _celebMoviesData.postValue(it)
            }
        }
    }

    private fun getCelebs(){
        viewModelScope.launch {
            getCelebsUseCase().cachedIn(viewModelScope).collectLatest {
                _celebRecommendationData.postValue(it)
            }
        }
    }

    fun getCelebTvSeries(personId:Int){
        viewModelScope.launch {
            getCelebTvSeriesUseCase(personId).collectLatest {
                _celebTvSeriesData.value=it
            }
        }
    }


}