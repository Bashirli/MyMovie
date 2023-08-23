package com.bashirli.mymovie.presentation.ui.fragment.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bashirli.mymovie.domain.model.celebs.CelebsResultModel
import com.bashirli.mymovie.domain.model.movies.ResultModel
import com.bashirli.mymovie.domain.useCase.remote.celeb.GetCelebsUseCase
import com.bashirli.mymovie.domain.useCase.remote.movies.GetForYouMoviesUseCase
import com.bashirli.mymovie.domain.useCase.remote.search.GetSearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMVVM @Inject constructor(
    private val getForYouMoviesUseCase: GetForYouMoviesUseCase,
    private val getCelebsUseCase: GetCelebsUseCase,
    private val getSearchMoviesUseCase: GetSearchMoviesUseCase
) : ViewModel() {

    private val _recommendationData=MutableLiveData<PagingData<ResultModel>>(PagingData.empty())
    val recommendationData: LiveData<PagingData<ResultModel>> = _recommendationData

    private val _celebsData=MutableLiveData<PagingData<CelebsResultModel>>(PagingData.empty())
    val celebsData: LiveData<PagingData<CelebsResultModel>> = _celebsData


    private val _searchData=MutableLiveData<PagingData<ResultModel>>(PagingData.empty())
    val searchData: LiveData<PagingData<ResultModel>> = _searchData


    init {
        getRecommendation()
        getCelebs()
    }

    private fun getRecommendation(){
        viewModelScope.launch {
            getForYouMoviesUseCase().cachedIn(viewModelScope).collectLatest {
                _recommendationData.postValue(it)
            }
        }
    }

    private fun getCelebs(){
        viewModelScope.launch {
            getCelebsUseCase().cachedIn(viewModelScope).collectLatest {
                _celebsData.postValue(it)
            }
        }
    }

    fun getSearch(query:String){
    viewModelScope.launch {
        getSearchMoviesUseCase(query).cachedIn(viewModelScope).collectLatest {
            _searchData.postValue(it)
        }
    }

    }


}