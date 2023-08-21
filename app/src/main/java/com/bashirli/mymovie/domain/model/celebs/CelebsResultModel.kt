package com.bashirli.mymovie.domain.model.celebs

import com.bashirli.mymovie.data.dto.celebs.KnownFor
import com.google.gson.annotations.SerializedName

data class CelebsResultModel(
    val adult: Boolean?,
    val gender: Int?,
    val id: Int?,
    val knownForDepartment: String?,
    val name: String?,
    val popularity: Double?,
    val profilePath: String?
)
