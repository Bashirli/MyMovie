package com.bashirli.mymovie.domain.useCase.remote.celeb

import com.bashirli.mymovie.domain.repository.remote.ApiRepository
import javax.inject.Inject

class GetCelebsUseCase @Inject constructor(
    private val repo: ApiRepository
) {

    suspend operator fun invoke()=repo.getCelebs()

}