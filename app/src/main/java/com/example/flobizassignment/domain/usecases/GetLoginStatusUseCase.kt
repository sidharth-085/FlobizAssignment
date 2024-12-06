package com.example.flobizassignment.domain.usecases

import com.example.flobizassignment.domain.repository.FirebaseAuthRepository
import javax.inject.Inject

class GetLoginStatusUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
) {
    operator fun invoke(): Boolean {
        return firebaseAuthRepository.isUserLoggedIn()?.let { true } ?: run { false }
    }
}