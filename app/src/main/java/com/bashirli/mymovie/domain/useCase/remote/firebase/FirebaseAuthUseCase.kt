package com.bashirli.mymovie.domain.useCase.remote.firebase

import com.bashirli.mymovie.domain.repository.remote.FirebaseRepository
import javax.inject.Inject

class FirebaseAuthUseCase @Inject constructor(private val repo: FirebaseRepository) {

    suspend fun registerUser(email:String , password:String) = repo.registerUser(email, password)

    suspend fun loginUser(email:String,password: String) = repo.loginUser(email, password)

    suspend fun getToken()=repo.getToken()

    suspend fun logOut()=repo.logOut()

}