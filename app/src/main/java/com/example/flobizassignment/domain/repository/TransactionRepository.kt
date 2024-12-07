package com.example.flobizassignment.domain.repository

import com.example.flobizassignment.domain.models.Transaction

interface TransactionRepository {
    suspend fun addTransaction(transaction: Transaction): Boolean

    suspend fun getTransactions(): List<Transaction>
}