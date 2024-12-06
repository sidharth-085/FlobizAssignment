package com.example.flobizassignment.domain.usecases

import com.example.flobizassignment.domain.repository.FirebaseAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLoginStatusUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return flow {
            firebaseAuthRepository.isUserLoggedIn()?.let { true } ?: run { false }
        }
    }
}