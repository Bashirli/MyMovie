package com.bashirli.mymovie.presentation.ui.fragment.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashirli.mymovie.common.util.Resource
import com.bashirli.mymovie.common.util.Status
import com.bashirli.mymovie.common.util.TokenManager
import com.bashirli.mymovie.data.dto.user.UserDTO
import com.bashirli.mymovie.domain.useCase.firebase.FirebaseAuthUseCase
import com.bashirli.mymovie.domain.useCase.firebase.FirebaseFirestoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class AuthMVVM @Inject constructor(
    private val firebaseAuthUseCase: FirebaseAuthUseCase,
    private val firebaseFirestoreUseCase: FirebaseFirestoreUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _liveData = MutableLiveData<AuthUiState>()
    val liveData : LiveData<AuthUiState> get()=_liveData

    fun loginUser(email:String,password:String){
        viewModelScope.launch {
            firebaseAuthUseCase.loginUser(email, password).collectLatest {
                when(it.status){
                    Status.LOADING->{
                        _liveData.value=AuthUiState.Loading
                    }
                    Status.ERROR->{
                        _liveData.value=AuthUiState.Error(it.message?:"Error")
                    }
                    Status.SUCCESS->{
                        _liveData.value=AuthUiState.Login
                    }
                }
            }
        }
    }


    fun registerUser(email:String,password:String){
        viewModelScope.launch {
            firebaseAuthUseCase.registerUser(email, password).collectLatest {
                when(it.status){
                    Status.SUCCESS->{
                        it.data?.let {
                            _liveData.value=AuthUiState.RegisterUser("success")
                        }
                    }
                    Status.ERROR->{
                        _liveData.value=AuthUiState.Error(it.message?:"Error")
                    }
                    Status.LOADING->{
                        _liveData.value=AuthUiState.Loading
                    }
                }
            }
        }
    }

    fun createUser(userDTO: UserDTO){
        viewModelScope.launch {
            firebaseFirestoreUseCase.createUser(userDTO).collectLatest {
                when(it.status){
                    Status.LOADING->{
                        _liveData.value=AuthUiState.Loading
                    }
                    Status.SUCCESS->{
                        _liveData.value= AuthUiState.CreateUser
                    }
                    Status.ERROR->{
                        _liveData.value= AuthUiState.Error(it.message?:"Error")
                    }
                }
            }
        }
    }

    fun getToken(){
        viewModelScope.launch {
            firebaseAuthUseCase.getToken().collectLatest {

                when(it.status){
                    Status.ERROR->{
                        _liveData.value=AuthUiState.Error(it.message?:"Error")
                    }
                    Status.SUCCESS->{
                        if(it.data!=null){
                            _liveData.value=AuthUiState.UserToken(it.data)

                        }else{
                            _liveData.value=AuthUiState.UserToken("")
                        }

                    }
                    Status.LOADING->{
                        _liveData.value=AuthUiState.Loading
                    }
                }
            }
        }
    }

    fun saveToken(token:String){
        tokenManager.setToken(token)
    }


    sealed class AuthUiState(){
        data class RegisterUser(val message:String):AuthUiState()

        object CreateUser : AuthUiState()

        object Login : AuthUiState()

        data class UserToken(val token : String) : AuthUiState()

        object Loading:AuthUiState()

        data class Error(val message:String) : AuthUiState()

    }

}