package com.bashirli.mymovie.domain.useCase.details

import com.bashirli.mymovie.domain.repository.ApiRepository
import javax.inject.Inject

class GetDetailsCreditUseCase @Inject constructor( private val repo:ApiRepository) {

    suspend operator fun invoke(movie_id:Int)=repo.getDetailsCredit(movie_id)

}