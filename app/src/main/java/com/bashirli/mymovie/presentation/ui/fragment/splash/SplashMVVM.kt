package com.bashirli.mymovie.presentation.ui.fragment.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bashirli.mymovie.common.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashMVVM @Inject constructor(
    private val tokenManager : TokenManager
) : ViewModel() {

    private val _isAuth=MutableLiveData<Boolean>(false)
    val isAuth:LiveData<Boolean> get()=_isAuth


    init {
        getToken()
    }

    private fun getToken(){
        val token = tokenManager.getToken()
        if(!token.isNullOrEmpty()){
            _isAuth.value=true
        }
    }

}