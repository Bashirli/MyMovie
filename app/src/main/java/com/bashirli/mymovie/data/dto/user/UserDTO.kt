package com.bashirli.mymovie.data.dto.user

import android.net.Uri

data class UserDTO(
    val email:String,
    val firstName : String,
    val lastName : String,
    val dateOfBirth : String,
    val token : String,
    val image: Uri?
)
