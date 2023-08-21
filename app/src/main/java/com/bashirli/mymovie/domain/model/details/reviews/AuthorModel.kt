package com.bashirli.mymovie.domain.model.details.reviews

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthorModel(
    val avatarPath: String?,
    val name: String?,
    val rating: Double?,
    val username: String
) : Parcelable
