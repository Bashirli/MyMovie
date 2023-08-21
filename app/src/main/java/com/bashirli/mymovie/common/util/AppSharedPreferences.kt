package com.bashirli.mymovie.common.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class AppSharedPreferences (private val context: Context) {

    private val masterKey: String by lazy {
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    }

    private val sp : SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            BASE_SP,
            masterKey,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
    }

    fun setToken(token:String){
        sp.edit().putString(TOKEN,token).apply()
    }

    fun removeToken(){
        sp.edit().remove(TOKEN).apply()
    }

    fun getToken():String?{
        return sp.getString(TOKEN,"")
    }

}