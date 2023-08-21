package com.bashirli.mymovie.domain.model.details.images

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BackdropModel(
    val filePath: String?,
) : Parcelable