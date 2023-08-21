package com.bashirli.mymovie.di

import android.content.Context
import com.bashirli.mymovie.common.util.AppSharedPreferences
import com.bashirli.mymovie.common.util.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object SpModule {

    @Singleton
    @Provides
    fun injectAppSharedPreferences(@ApplicationContext context : Context) = AppSharedPreferences(context)

    @Singleton
    @Provides
    fun injectTokenManager(sp:AppSharedPreferences) = TokenManager(sp)


}