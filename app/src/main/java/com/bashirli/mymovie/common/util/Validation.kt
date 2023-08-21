package com.bashirli.mymovie.common.util

import android.util.Patterns

fun validateEmail(email:String) : Boolean {
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches()
}

fun validatePassword(password:String) : Boolean{
    return password.length>=6
}

fun validateEmptyField(text:String?) : Boolean{
    return !text.isNullOrEmpty()
}