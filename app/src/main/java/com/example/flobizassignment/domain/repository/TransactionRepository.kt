package com.example.flobizassignment.domain.repository

import com.example.flobizassignment.domain.models.Transaction

interface TransactionRepository {
    suspend fun addTransaction(transaction: Transaction): Boolean

    suspend fun getTransactions(): List<Transaction>

    suspend fun updateTransaction(
        transaction: Transaction,
        onSuccessCallback: () -> Unit,
        onFailureCallback: (e: Exception) -> Unit
    )

    suspend fun deleteTransaction(
        transactionId: String,
        onSuccessCallback: () -> Unit,
        onFailureCallback: (e: Exception) -> Unit
    )
}