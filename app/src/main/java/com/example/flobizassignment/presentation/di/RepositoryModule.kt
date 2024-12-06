package com.example.flobizassignment.presentation.di

import com.example.flobizassignment.data.repository.FireStoreRepositoryImpl
import com.example.flobizassignment.domain.repository.FireStoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFireStoreRepository(
        fireStoreRepositoryImpl: FireStoreRepositoryImpl
    ): FireStoreRepository
}