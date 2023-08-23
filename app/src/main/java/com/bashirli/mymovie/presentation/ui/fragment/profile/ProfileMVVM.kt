package com.bashirli.mymovie.presentation.ui.fragment.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashirli.mymovie.common.util.Status
import com.bashirli.mymovie.common.util.TokenManager
import com.bashirli.mymovie.domain.model.user.UserModel
import com.bashirli.mymovie.domain.useCase.remote.firebase.FirebaseAuthUseCase
import com.bashirli.mymovie.domain.useCase.remote.firebase.FirebaseFirestoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMVVM @Inject constructor(
    private val getFirebaseFirestoreUseCase: FirebaseFirestoreUseCase,
    private val getFirebaseAuthUseCase: FirebaseAuthUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _liveData = MutableLiveData<ProfileState>()
    val liveData : LiveData<ProfileState> get()=_liveData

    init {
        getToken()
    }


    private fun getToken(){
        val token = tokenManager.getToken()
        if(token.isNullOrEmpty()){
            _liveData.value=ProfileState.Error("token is null")
        }else{
            _liveData.value=ProfileState.Token(token)
        }
    }

    private fun getUserData(token:String){
        viewModelScope.launch {
            getFirebaseFirestoreUseCase.getUser(token).collectLatest {
                when(it.status){
                    Status.SUCCESS->{
                        it.data?.let {
                            _liveData.value=ProfileState.UserData(it)
                        }
                    }
                    Status.ERROR-> {
                        _liveData.value=ProfileState.Error(it.message?:"Error")
                    }
                    Status.LOADING->{
                        _liveData.value=ProfileState.Loading
                    }
                }
            }
        }
    }

    private fun logOut(){
        viewModelScope.launch {
            getFirebaseAuthUseCase.logOut().collectLatest {
                when(it.status){
                    Status.SUCCESS -> {
                        _liveData.value=ProfileState.LogOut
                    }
                    Status.ERROR -> {
                        _liveData.value=ProfileState.Error(it.message?:"Error")
                    }
                    Status.LOADING -> _liveData.value=ProfileState.Loading
                }
            }
        }
    }

    private fun removeToken(){
        tokenManager.removeToken()
        _liveData.value=ProfileState.TokenRemover
    }

    fun onEvent(event : ProfileEvent){
        when(event){
            is ProfileEvent.GetUserData -> {
                getUserData(event.token)
            }
            ProfileEvent.LogOut -> {
                logOut()
            }
            ProfileEvent.RemoveToken -> {
                removeToken()
            }
        }


    }


    sealed class ProfileState(){

        data class UserData(val data : UserModel):ProfileState()

        data class Token(val token:String) : ProfileState()

        object LogOut : ProfileState()

        object TokenRemover : ProfileState()

        object Loading : ProfileState()

        data class Error(val message:String) : ProfileState()

    }


    sealed class ProfileEvent(){

        data class GetUserData (val token:String) : ProfileEvent()

        object LogOut : ProfileEvent()

        object RemoveToken : ProfileEvent()

    }



}