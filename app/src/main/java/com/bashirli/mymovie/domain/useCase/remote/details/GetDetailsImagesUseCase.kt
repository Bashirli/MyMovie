package com.bashirli.mymovie.domain.useCase.remote.details

import com.bashirli.mymovie.domain.repository.remote.ApiRepository
import javax.inject.Inject

class GetDetailsImagesUseCase @Inject constructor(private val repo : ApiRepository) {

    suspend operator fun invoke(movie_id:Int)=repo.getDetailsImages(movie_id)

}