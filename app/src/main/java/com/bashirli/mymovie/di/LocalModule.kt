package com.bashirli.mymovie.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bashirli.mymovie.data.repository.local.FavoritesRepositoryImpl
import com.bashirli.mymovie.data.service.local.RoomDAO
import com.bashirli.mymovie.data.service.local.RoomDB
import com.bashirli.mymovie.data.source.local.FavoritesSource
import com.bashirli.mymovie.data.source.local.FavoritesSourceImpl
import com.bashirli.mymovie.domain.repository.local.FavoritesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun injectRoomDB(@ApplicationContext context:Context) = Room
        .databaseBuilder(
        context,
        RoomDB::class.java,
        "FavoritesLocalDB"
    ).build()

    @Singleton
    @Provides
    fun injectDAO(roomDB: RoomDB)=roomDB.getDao()


    @Singleton
    @Provides
    fun injectFavSource(roomDAO: RoomDAO)=FavoritesSourceImpl(roomDAO) as FavoritesSource

    @Singleton
    @Provides
    fun injectFavRepo(source:FavoritesSource) = FavoritesRepositoryImpl(source) as FavoritesRepository


}