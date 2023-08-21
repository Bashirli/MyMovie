package com.bashirli.mymovie.di

import android.content.Context
import com.bashirli.mymovie.common.util.BASE_URL
import com.bashirli.mymovie.data.repository.ApiRepositoryImpl
import com.bashirli.mymovie.data.service.ApiKeyInterceptor
import com.bashirli.mymovie.data.service.Service
import com.bashirli.mymovie.data.source.ApiSource
import com.bashirli.mymovie.data.source.ApiSourceImpl
import com.bashirli.mymovie.domain.repository.ApiRepository
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun injectInterceptor() = ApiKeyInterceptor()

    @Singleton
    @Provides
    fun injectOkHttp3(interceptor: ApiKeyInterceptor, @ApplicationContext context: Context) =
            OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .addInterceptor(ChuckerInterceptor(context))
            .build()


    @Singleton
    @Provides
    fun injectRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

    @Singleton
    @Provides
    fun injectService(retrofit: Retrofit) = retrofit.create(Service::class.java)


    @Singleton
    @Provides
    fun injectSource(service: Service) = ApiSourceImpl(service) as ApiSource

    @Singleton
    @Provides
    fun injectRepo(source: ApiSource) = ApiRepositoryImpl(source) as ApiRepository


}