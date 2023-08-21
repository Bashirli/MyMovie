package com.bashirli.mymovie.di

import com.bashirli.mymovie.data.repository.FirebaseRepositoryImpl
import com.bashirli.mymovie.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn (SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun injectAuth()=FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun injectFirestore()=FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun injectStorage()=FirebaseStorage.getInstance()

    @Singleton
    @Provides
    fun injectRepo(firebaseAuth: FirebaseAuth,firestore: FirebaseFirestore,storage:FirebaseStorage) = FirebaseRepositoryImpl(firebaseAuth,firestore, storage) as FirebaseRepository

}