package com.example.flobizassignment.domain.usecases

import com.example.flobizassignment.domain.models.Transaction
import com.example.flobizassignment.domain.repository.TransactionRepository
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction): Boolean {
        return transactionRepository.addTransaction(transaction)
    }
}