package com.bashirli.mymovie.domain.useCase.remote.details

import com.bashirli.mymovie.domain.repository.remote.ApiRepository
import javax.inject.Inject

class GetTvSeriesDetailsUseCase @Inject constructor(
    private val repo: ApiRepository
) {

    suspend operator fun invoke(seriesId:Int) = repo.getTvSeriesDetails(seriesId)
    suspend fun images(seriesId:Int) = repo.getTvSeriesDetailsImages(seriesId)
    suspend fun credits(seriesId:Int) = repo.getTvSeriesCredit(seriesId)
    suspend fun recommendations(seriesId:Int) = repo.getTvSeriesRecommendations(seriesId)
    suspend fun reviews(seriesId:Int) = repo.getTvSeriesReviews(seriesId)

}