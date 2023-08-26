package com.bashirli.mymovie.presentation.ui.fragment.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashirli.mymovie.common.util.Status
import com.bashirli.mymovie.domain.model.categorySelected.CategoryDetailsModel
import com.bashirli.mymovie.domain.useCase.remote.categorySelected.GetCategoryDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryDetailsMVVM @Inject constructor(
    private val getCategoryDetailsUseCase: GetCategoryDetailsUseCase
) : ViewModel() {

    private val _liveData = MutableLiveData<CategoryDetailsState>()
    val liveData : LiveData<CategoryDetailsState> get()=_liveData


    fun getDetails(listId:Int){
        viewModelScope.launch {
            getCategoryDetailsUseCase(listId).collectLatest {
                when(it.status){
                    Status.SUCCESS -> {
                        it.data?.let {
                            _liveData.value=CategoryDetailsState.Details(it)
                        }
                    }
                    Status.ERROR -> {
                        _liveData.value=CategoryDetailsState.Error(it.message?:"Error")
                    }
                    Status.LOADING -> _liveData.value=CategoryDetailsState.Loading
                }
            }
        }
    }



    sealed class CategoryDetailsState(){
        object Loading : CategoryDetailsState()

        data class Error(val message:String) : CategoryDetailsState()

        data class Details(val data : CategoryDetailsModel) : CategoryDetailsState()


    }

}