package com.bashirli.mymovie.domain.useCase.celeb

import com.bashirli.mymovie.domain.repository.ApiRepository
import javax.inject.Inject

class GetCelebsUseCase @Inject constructor(
    private val repo:ApiRepository
) {

    suspend operator fun invoke()=repo.getCelebs()

}