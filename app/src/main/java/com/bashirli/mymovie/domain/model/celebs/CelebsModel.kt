package com.bashirli.mymovie.domain.model.celebs

import com.bashirli.mymovie.data.dto.celebs.CelebsResult
import com.google.gson.annotations.SerializedName

data class CelebsModel(
    val page: Int?,
    val celebsResults: List<CelebsResultModel?>?,
)