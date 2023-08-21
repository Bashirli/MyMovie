package com.bashirli.mymovie.domain.repository

import com.bashirli.mymovie.common.util.Resource
import com.bashirli.mymovie.data.dto.user.UserDTO
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    suspend fun registerUser (email:String , password : String) : Flow<Resource<AuthResult>>

    suspend fun createUser (userDTO: UserDTO) : Flow<Resource<String>>

    suspend fun loginUser(email:String, password:String) : Flow<Resource<AuthResult>>

    suspend fun getToken():Flow<Resource<String>>

}