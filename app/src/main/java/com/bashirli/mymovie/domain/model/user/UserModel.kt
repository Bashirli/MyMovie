package com.bashirli.mymovie.domain.model.user

import android.net.Uri

data class UserModel(
    val email:String,
    val firstName : String,
    val lastName : String,
    val dateOfBirth : String,
    val image: String
)
