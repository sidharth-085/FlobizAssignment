package com.example.flobizassignment.data.repository

import com.example.flobizassignment.domain.repository.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): FirebaseAuthRepository {

    override fun isUserLoggedIn(): Boolean? {
        return firebaseAuth.currentUser?.let { true }
    }
}