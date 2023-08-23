package com.bashirli.mymovie.domain.model.local

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class FavoritesModel(
    val id : Int?,
    val title : String?,
    val voteAverage : Double?,
    val releaseYear : String?,
    val image : String?
)