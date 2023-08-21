package com.bashirli.mymovie.presentation.ui.fragment.details.tvseries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bashirli.mymovie.R
import com.bashirli.mymovie.common.util.Resource
import com.bashirli.mymovie.common.util.Status
import com.bashirli.mymovie.domain.model.details.cast.CastBaseModel
import com.bashirli.mymovie.domain.model.details.images.ImagesModel
import com.bashirli.mymovie.domain.model.details.reviews.ReviewModel
import com.bashirli.mymovie.domain.model.details.tvseries.TvSeriesDetailsModel
import com.bashirli.mymovie.domain.model.tvseries.TvSeriesResultModel
import com.bashirli.mymovie.domain.useCase.details.GetTvSeriesDetailsUseCase
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvSeriesDetailsMVVM @Inject constructor(
        private val getTvSeriesDetailsUseCase: GetTvSeriesDetailsUseCase
) : ViewModel() {

    private val _liveData = MutableLiveData<State>()
    val liveData : LiveData<State> get()=_liveData

    private val _recommendationsData = MutableLiveData<PagingData<TvSeriesResultModel>>(PagingData.empty())
    val recommendationsData : LiveData<PagingData<TvSeriesResultModel>> get()=_recommendationsData

    fun getDetails(seriesId:Int){
        viewModelScope.launch {
            getTvSeriesDetailsUseCase(seriesId).collect{
               when(it.status){
                   Status.SUCCESS->{
                       it.data?.let {
                           _liveData.value=State.DetailsData(it)
                       }
                   }
                   Status.ERROR->{
                        _liveData.value=State.Error(it.message?:"Error")
                   }
                   Status.LOADING->{
                       _liveData.value=State.Loading
                   }
               }
            }
        }
    }

    fun getReviews(seriesId: Int){
        viewModelScope.launch {
            getTvSeriesDetailsUseCase.reviews(seriesId).collect{
                when(it.status){
                    Status.SUCCESS->{
                        it.data?.let {
                            _liveData.value=State.ReviewsData(it)
                        }
                    }
                    Status.ERROR->{
                        _liveData.value=State.Error(it.message?:"Error")
                    }
                    Status.LOADING->{
                    }
                }
            }
        }
    }

    fun getImages(seriesId: Int){
        viewModelScope.launch {
            getTvSeriesDetailsUseCase.images(seriesId).collect{
                when(it.status){
                    Status.SUCCESS->{
                        it.data?.let {
                            _liveData.value=State.ImagesData(it)
                        }
                    }
                    Status.ERROR->{
                        _liveData.value=State.Error(it.message?:"Error")
                    }
                    Status.LOADING->{
                    }
                }
            }
        }
    }

    fun getRecommendations(seriesId: Int){
        viewModelScope.launch {
            getTvSeriesDetailsUseCase.recommendations(seriesId).cachedIn(this).collectLatest {
                _recommendationsData.value=it
            }
        }
    }

    fun getCredits(seriesId: Int){
        viewModelScope.launch {
            getTvSeriesDetailsUseCase.credits(seriesId).collect{
                when(it.status){
                    Status.SUCCESS->{
                        it.data?.let {
                            _liveData.value=State.CreditsData(it)
                        }
                    }
                    Status.ERROR->{
                        _liveData.value=State.Error(it.message?:"Error")
                    }
                    Status.LOADING->{
                    }
                }
            }
        }
    }


    sealed class State(){

        data class DetailsData(val data : TvSeriesDetailsModel) : State()

        data class CreditsData(val data : CastBaseModel) : State()

        data class ReviewsData(val data : ReviewModel) : State()

        data class ImagesData(val data : ImagesModel) : State()

        data class Error(val message : String):State()

        object Loading : State()

    }


}