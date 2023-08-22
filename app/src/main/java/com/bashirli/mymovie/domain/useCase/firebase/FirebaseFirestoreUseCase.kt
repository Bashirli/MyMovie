package com.bashirli.mymovie.domain.useCase.firebase

import com.bashirli.mymovie.data.dto.user.UserDTO
import com.bashirli.mymovie.domain.repository.ApiRepository
import com.bashirli.mymovie.domain.repository.FirebaseRepository
import javax.inject.Inject

class FirebaseFirestoreUseCase @Inject constructor(
    private val repo:FirebaseRepository
) {

    suspend fun createUser(userDTO: UserDTO) = repo.createUser(userDTO)

    suspend fun getUser(token:String) = repo.getUserData(token)

}