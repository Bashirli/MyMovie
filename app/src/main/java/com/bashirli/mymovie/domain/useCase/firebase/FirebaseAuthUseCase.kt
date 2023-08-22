package com.bashirli.mymovie.domain.useCase.firebase

import com.bashirli.mymovie.domain.repository.FirebaseRepository
import javax.inject.Inject

class FirebaseAuthUseCase @Inject constructor(private val repo:FirebaseRepository) {

    suspend fun registerUser(email:String , password:String) = repo.registerUser(email, password)

    suspend fun loginUser(email:String,password: String) = repo.loginUser(email, password)

    suspend fun getToken()=repo.getToken()

    suspend fun logOut()=repo.logOut()

}