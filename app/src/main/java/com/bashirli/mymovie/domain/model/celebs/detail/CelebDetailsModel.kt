package com.bashirli.mymovie.domain.model.celebs.detail

import com.google.gson.annotations.SerializedName

data class CelebDetailsModel(
    val adult: Boolean?,
    val alsoKnownAs: List<String?>?,
    val biography: String?,
    val birthday: String?,
    val deathday: Any?,
    val gender: Int?,
    val homepage: Any?,
    val id: Int?,
    val knownForDepartment: String?,
    val name: String?,
    val placeOfBirth: String?,
    val popularity: Double?,
    val profilePath: String?
)


