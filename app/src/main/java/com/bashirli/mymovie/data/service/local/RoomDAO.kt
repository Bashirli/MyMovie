package com.bashirli.mymovie.data.service.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bashirli.mymovie.data.dto.local.FavoritesDTO

@Dao
interface RoomDAO {

    @Query("SELECT * FROM FavoritesTable")
    suspend fun getAllFavorites() : List<FavoritesDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(favoritesDTO: FavoritesDTO)

    @Query("Delete from FavoritesTable where id=:id")
    suspend fun deleteItem (id : Int)

    @Query("Select exists(select * from FavoritesTable where id = :id)")
    suspend fun checkItemHaveInTable(id:Int) : Boolean

}