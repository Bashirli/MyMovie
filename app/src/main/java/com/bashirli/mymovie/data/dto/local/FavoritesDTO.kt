package com.bashirli.mymovie.data.dto.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey

@Entity(tableName = "FavoritesTable")
data class FavoritesDTO(
    @PrimaryKey(autoGenerate = false)
    val id : Int?,
    @ColumnInfo("title")
    val title : String?,
    @ColumnInfo("voteAverage")
    val voteAverage : Double?,
    @ColumnInfo("releaseYear")
    val releaseYear : String?,
    @ColumnInfo("image")
    val image : String?
)
