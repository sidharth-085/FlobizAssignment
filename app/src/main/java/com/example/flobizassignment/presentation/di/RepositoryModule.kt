package com.example.flobizassignment.presentation.di

import com.example.flobizassignment.data.repository.TransactionRepositoryImpl
import com.example.flobizassignment.data.repository.FirebaseAuthRepositoryImpl
import com.example.flobizassignment.domain.repository.TransactionRepository
import com.example.flobizassignment.domain.repository.FirebaseAuthRepository
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
    abstract fun bindTransactionRepository(
        transactionRepositoryImpl: TransactionRepositoryImpl
    ): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindFirebaseAuthRepository(
        firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl
    ): FirebaseAuthRepository
}