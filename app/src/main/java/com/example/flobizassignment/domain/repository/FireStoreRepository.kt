package com.example.flobizassignment.domain.repository

import com.example.flobizassignment.domain.models.Expense

interface FireStoreRepository {
    suspend fun addExpense(expense: Expense): Boolean
}