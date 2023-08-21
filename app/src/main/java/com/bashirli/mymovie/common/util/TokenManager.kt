package com.bashirli.mymovie.common.util

import javax.inject.Inject

class TokenManager @Inject constructor(
    private val sp:AppSharedPreferences
) {

    fun setToken(token:String){
        sp.setToken(token)
    }

    fun removeToken(){
        sp.removeToken()
    }

    fun getToken():String?{
        return sp.getToken()
    }


}