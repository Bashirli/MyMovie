package com.bashirli.mymovie.data.service.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bashirli.mymovie.data.dto.local.FavoritesDTO

@Database(entities = [FavoritesDTO::class], version = 1)
abstract class RoomDB : RoomDatabase() {
    abstract fun getDao():RoomDAO
}