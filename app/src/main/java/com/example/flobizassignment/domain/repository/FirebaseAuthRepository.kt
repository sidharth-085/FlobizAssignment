package com.example.flobizassignment.domain.repository

interface FirebaseAuthRepository {
    fun isUserLoggedIn(): Boolean?
}